package com.l2bot.bridge.models.skill

import com.l2bot.bridge.models.L2Object
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Класс, описывающий бафф или дебафф
 * Class describing a buff or debuff
 */
@Serializable
open class L2Buff : L2Object() {
    /** Уровень баффа/дебаффа | Skill/Buff level */
    var level: Int = 0

    /** Подуровень баффа/дебаффа (заточка) | Skill/Buff sub-level (enchant) */
    @SerialName("level2")
    var level2: Int = 0

    /** Момент времени (GetTickCount), когда бафф/дебафф был наложен | Start time (GetTickCount) when buff was applied */
    @SerialName("start_time")
    var startTime: Long = 0

    /** Момент времени (GetTickCount), когда бафф/дебафф закончится | End time (GetTickCount) when buff will expire */
    @SerialName("end_time")
    var endTime: Long = 0

    /** Общее время отката баффа/дебаффа | Total reuse time of buff/debuff */
    @SerialName("reuse_time")
    var reuseTime: Int = 0
}