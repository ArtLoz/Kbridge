package com.l2bot.bridge.models.skill

import com.l2bot.bridge.models.interfaces.IL2LearnItem
import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Data class for skills available for learning.
 * Data class для умений, доступных для изучения.
 */
@Serializable
data class LearnItem(
    // IL2Object fields
    override val name: String = "",
    override val id: Int = 0,
    override val oid: Int = 0,
    override val valid: Boolean = false,
    @SerialName("l2_class")
    override val l2Class: L2Class = L2Class.UNKNOWN,

    // IL2Buff fields
    override val level: Int = 0,
    @SerialName("level2")
    override val level2: Int = 0,
    @SerialName("start_time")
    override val startTime: Long = 0,
    @SerialName("end_time")
    override val endTime: Long = 0,
    @SerialName("reuse_time")
    override val reuseTime: Int = 0,

    // IL2Skill fields
    @SerialName("is_passive")
    override val isPassive: Boolean = false,
    @SerialName("is_disabled")
    override val isDisabled: Boolean = false,
    @SerialName("is_enchanted")
    override val isEnchanted: Boolean = false,
    override val range: Int = 0,
    @SerialName("is_aura")
    override val isAura: Boolean = false,

    // IL2LearnItem fields
    @SerialName("need_level")
    override val needLevel: Int = 0,
    @SerialName("max_level")
    override val maxLevel: Int = 0,
    @SerialName("sp_cost")
    override val spCost: Long = 0,
    override val requirements: Int = 0,
    override val group: Int = 0
) : IL2LearnItem
