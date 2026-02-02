package com.l2bot.bridge.models.skill

import com.l2bot.bridge.models.interfaces.IL2Buff
import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Data class describing a buff or debuff.
 * Data class для баффа или дебаффа.
 */
@Serializable
data class L2Buff(
    // IL2Object fields
    override val name: String = "",
    override val id: Int = 0,
    override val oid: Int = 0,
    override val valid: Boolean = false,
    @SerialName("l2_class")
    override val l2Class: L2Class = L2Class.UNKNOWN,

    // IL2Buff fields
    /** Buff level | Уровень баффа */
    override val level: Int = 0,
    /** Buff sub-level (enchant) | Подуровень (заточка) */
    @SerialName("level2")
    override val level2: Int = 0,
    /** Start time (GetTickCount) | Время начала */
    @SerialName("start_time")
    override val startTime: Long = 0,
    /** End time (GetTickCount) | Время окончания */
    @SerialName("end_time")
    override val endTime: Long = 0,
    /** Total reuse time | Время отката */
    @SerialName("reuse_time")
    override val reuseTime: Int = 0
) : IL2Buff
