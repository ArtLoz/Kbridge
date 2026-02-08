package com.l2bot.bridge.core

import com.l2bot.bridge.api.L2Bot
import com.l2bot.bridge.api.L2Gps
import com.l2bot.bridge.models.gps.GpsPoint
import java.sql.DriverManager
import java.util.PriorityQueue
import kotlin.math.sqrt

internal class GpsNavigatorImpl(
    private val bot: L2Bot
) : L2Gps {

    private val points = mutableMapOf<Int, GpsPoint>()
    private val links = mutableMapOf<Int, MutableList<Int>>()
    private var loaded = false

    override fun loadBase(path: String): Int {
        points.clear()
        links.clear()

        DriverManager.getConnection("jdbc:sqlite:$path").use { conn ->
            val stmt = conn.createStatement()

            val prs = stmt.executeQuery(
                "SELECT id, x, y, z, name, radius FROM point WHERE temp_point = 0"
            )
            while (prs.next()) {
                val p = GpsPoint(
                    id = prs.getInt("id"),
                    x = prs.getDouble("x"),
                    y = prs.getDouble("y"),
                    z = prs.getDouble("z"),
                    name = prs.getString("name") ?: "",
                    radius = prs.getDouble("radius")
                )
                points[p.id] = p
                links[p.id] = mutableListOf()
            }

            val lrs = stmt.executeQuery(
                "SELECT start_point_id, end_point_id, one_way FROM link WHERE temp_link = 0"
            )
            while (lrs.next()) {
                val startId = lrs.getInt("start_point_id")
                val endId = lrs.getInt("end_point_id")
                val oneWay = lrs.getInt("one_way") == 1

                links[startId]?.add(endId)
                if (!oneWay) {
                    links[endId]?.add(startId)
                }
            }
        }

        loaded = true
        return points.size
    }

    override fun getPath(
        fromX: Double, fromY: Double, fromZ: Double,
        toX: Double, toY: Double, toZ: Double
    ): List<GpsPoint> {
        if (!loaded) return emptyList()

        val start = findNearest(fromX, fromY, fromZ) ?: return emptyList()
        val end = findNearest(toX, toY, toZ) ?: return emptyList()

        if (start.id == end.id) return listOf(end)
        return aStar(start.id, end.id)
    }

    override fun getPathByName(
        fromX: Double, fromY: Double, fromZ: Double,
        spotName: String
    ): List<GpsPoint> {
        if (!loaded) return emptyList()

        val start = findNearest(fromX, fromY, fromZ) ?: return emptyList()
        val end = points.values.find { it.name.equals(spotName, ignoreCase = true) }
            ?: return emptyList()

        if (start.id == end.id) return listOf(end)
        return aStar(start.id, end.id)
    }

    override fun getPointByName(name: String): GpsPoint? {
        return points.values.find { it.name.equals(name, ignoreCase = true) }
    }

    override suspend fun navigateTo(x: Double, y: Double, z: Double): Boolean {
        val user = bot.user()
        val path = getPath(
            user.x.toDouble(), user.y.toDouble(), user.z.toDouble(),
            x, y, z
        )
        if (path.isEmpty()) return false
        return walkPath(path)
    }

    override suspend fun navigateTo(spotName: String): Boolean {
        val user = bot.user()
        val path = getPathByName(
            user.x.toDouble(), user.y.toDouble(), user.z.toDouble(),
            spotName
        )
        if (path.isEmpty()) return false
        return walkPath(path)
    }

    private suspend fun walkPath(path: List<GpsPoint>): Boolean {
        for (point in path) {
            val success = bot.moveTo(point.x.toInt(), point.y.toInt(), point.z.toInt())
            if (!success) return false
        }
        return true
    }


    private fun findNearest(x: Double, y: Double, z: Double): GpsPoint? {
        return points.values.minByOrNull { dist(it, x, y, z) }
    }

    private fun dist(p: GpsPoint, x: Double, y: Double, z: Double): Double {
        val dx = p.x - x
        val dy = p.y - y
        val dz = p.z - z
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    private fun distBetween(a: GpsPoint, b: GpsPoint): Double {
        return dist(a, b.x, b.y, b.z)
    }

    private fun aStar(startId: Int, endId: Int): List<GpsPoint> {
        val end = points[endId] ?: return emptyList()

        data class Node(val id: Int, val f: Double)

        val gScore = mutableMapOf<Int, Double>().withDefault { Double.MAX_VALUE }
        val cameFrom = mutableMapOf<Int, Int>()
        val closed = mutableSetOf<Int>()
        val open = PriorityQueue<Node>(compareBy { it.f })

        gScore[startId] = 0.0
        open.add(Node(startId, distBetween(points[startId]!!, end)))

        while (open.isNotEmpty()) {
            val current = open.poll()

            if (current.id == endId) {
                return reconstructPath(cameFrom, endId)
            }

            if (current.id in closed) continue
            closed.add(current.id)

            val neighbors = links[current.id] ?: continue
            for (neighborId in neighbors) {
                if (neighborId in closed) continue
                val neighbor = points[neighborId] ?: continue
                val currentPoint = points[current.id] ?: continue

                val tentativeG = gScore.getValue(current.id) + distBetween(currentPoint, neighbor)

                if (tentativeG < gScore.getValue(neighborId)) {
                    cameFrom[neighborId] = current.id
                    gScore[neighborId] = tentativeG
                    open.add(Node(neighborId, tentativeG + distBetween(neighbor, end)))
                }
            }
        }

        return emptyList()
    }

    private fun reconstructPath(cameFrom: Map<Int, Int>, endId: Int): List<GpsPoint> {
        val path = mutableListOf<GpsPoint>()
        var current = endId
        while (current in cameFrom) {
            points[current]?.let { path.add(0, it) }
            current = cameFrom[current]!!
        }
        points[current]?.let { path.add(0, it) }
        return path
    }
}
