package com.l2bot.bridge.models.entities

import com.l2bot.bridge.models.L2Object
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Базовый класс игровых объектов, имеющих координаты
 * Base class for game objects with coordinates
 */
@Serializable
open class L2Spawn : L2Object() {
    /** Координата X объекта | X coordinate of object */
    var x: Int = 0

    /** Координата Y объекта | Y coordinate of object */
    var y: Int = 0

    /** Координата Z объекта | Z coordinate of object */
    var z: Int = 0

    /** Момент времени (GetTickCount) появления/спавна объекта (в мс, 1сек = 1000мс) | Spawn time moment (GetTickCount) in ms */
    @SerialName("spawn_time")
    var spawnTime: Int = 0

    /** Находится ли объект в зоне, настроенной в боте | Is object in bot-configured zone */
    @SerialName("in_zone")
    var inZone: Boolean = false

    /** Получить дистанцию от объекта до другого объекта/точки | Get distance from object to another object/point */
    fun distTo(
        model: L2Spawn
    ): Int {
        return distTo(x = model.x, y = model.y, z = model.z)
    }

    /** Получить дистанцию от объекта до другого объекта/точки | Get distance from object to another object/point */
    fun distTo(
        x: Int, y: Int, z: Int
    ): Int {
        val dx = (x - this.x).toDouble()
        val dy = (y - this.y).toDouble()
        val dz = (z - this.z).toDouble()
        return sqrt(dx * dx + dy * dy + dz * dz).toInt()
    }
    /** Находится ли объект в зоне, настроенной в боте | Is object in bot-configured zone */
    fun inRange(x: Int, y: Int, z: Int, range: Int, zLimit: Int = 250): Boolean {
        if (abs(z - this.z) > zLimit) return false
        val dx = (x - this.x).toDouble()
        val dy = (y - this.y).toDouble()
        val dz = (z - this.z).toDouble()
        return sqrt(dx * dx + dy * dy + dz * dz) <= range
    }

}