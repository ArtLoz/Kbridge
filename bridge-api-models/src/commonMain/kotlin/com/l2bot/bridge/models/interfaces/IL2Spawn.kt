package com.l2bot.bridge.models.interfaces

import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Interface for game objects with coordinates.
 * Интерфейс для игровых объектов с координатами.
 */
interface IL2Spawn : IL2Object {
    /** X coordinate | X координата */
    val x: Int
    /** Y coordinate | Y координата */
    val y: Int
    /** Z coordinate | Z координата */
    val z: Int
    /** Spawn time (GetTickCount) in ms | Время спавна в мс */
    val spawnTime: Int
    /** Is object in bot-configured zone | Находится ли объект в зоне бота */
    val inZone: Boolean
}

/** Get distance to another object | Получить дистанцию до другого объекта */
fun IL2Spawn.distTo(other: IL2Spawn): Int = distTo(other.x, other.y, other.z)

/** Get distance to point | Получить дистанцию до точки */
fun IL2Spawn.distTo(x: Int, y: Int, z: Int): Int {
    val dx = (x - this.x).toDouble()
    val dy = (y - this.y).toDouble()
    val dz = (z - this.z).toDouble()
    return sqrt(dx * dx + dy * dy + dz * dz).toInt()
}

/** Check if point is in range | Проверить, находится ли точка в радиусе */
fun IL2Spawn.inRange(x: Int, y: Int, z: Int, range: Int, zLimit: Int = 250): Boolean {
    if (abs(z - this.z) > zLimit) return false
    val dx = (x - this.x).toDouble()
    val dy = (y - this.y).toDouble()
    val dz = (z - this.z).toDouble()
    return sqrt(dx * dx + dy * dy + dz * dz) <= range
}

/** Check if object is in range | Проверить, находится ли объект в радиусе */
fun IL2Spawn.inRange(other: IL2Spawn, range: Int, zLimit: Int = 250): Boolean =
    inRange(other.x, other.y, other.z, range, zLimit)
