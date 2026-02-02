package com.l2bot.bridge.models.entities

import com.l2bot.bridge.models.interfaces.IL2Spawn
import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class for game objects with coordinates.
 * Data class для игровых объектов с координатами.
 */
@Serializable
data class L2Spawn(
    // IL2Object fields
    override val name: String = "",
    override val id: Int = 0,
    override val oid: Int = 0,
    override val valid: Boolean = false,
    @SerialName("l2_class")
    override val l2Class: L2Class = L2Class.UNKNOWN,

    // IL2Spawn fields
    /** X coordinate | X координата */
    override val x: Int = 0,
    /** Y coordinate | Y координата */
    override val y: Int = 0,
    /** Z coordinate | Z координата */
    override val z: Int = 0,
    /** Spawn time (GetTickCount) in ms | Время спавна в мс */
    @SerialName("spawn_time")
    override val spawnTime: Int = 0,
    /** Is object in bot-configured zone | Находится ли объект в зоне бота */
    @SerialName("in_zone")
    override val inZone: Boolean = false
) : IL2Spawn
