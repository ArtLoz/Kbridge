package com.l2bot.bridge.models.item

import com.l2bot.bridge.models.interfaces.IL2Drop
import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Data class for items on the ground (drop).
 * Data class для предметов на земле (дроп).
 */
@Serializable
data class L2Drop(
    // IL2Object fields
    override val name: String = "",
    override val id: Int = 0,
    override val oid: Int = 0,
    override val valid: Boolean = false,
    @SerialName("l2_class")
    override val l2Class: L2Class = L2Class.UNKNOWN,

    // IL2Spawn fields
    override val x: Int = 0,
    override val y: Int = 0,
    override val z: Int = 0,
    @SerialName("spawn_time")
    override val spawnTime: Int = 0,
    @SerialName("in_zone")
    override val inZone: Boolean = false,

    // IL2Drop fields
    override val count: Long = 0,
    @SerialName("owner_oid")
    override val ownerOid: Int = 0,
    override val stackable: Boolean = false,
    @SerialName("is_my")
    override val isMy: Boolean = false
) : IL2Drop
