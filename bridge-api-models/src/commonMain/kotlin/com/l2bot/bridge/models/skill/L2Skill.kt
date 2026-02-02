package com.l2bot.bridge.models.skill

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс умений персонажа | Character skills interface
 */
@Serializable
open class L2Skill : L2Buff() {
    /** Является ли умение пассивным | Whether the skill is passive */
    @SerialName("is_passive")
    var isPassive: Boolean = false

    /** Заблокировано ли умение (например, из-за нехватки ресурсов или условий) | Whether the skill is disabled */
    @SerialName("is_disabled")
    var isDisabled: Boolean = false

    /** Является ли умение заточенным | Whether the skill is enchanted */
    @SerialName("is_enchanted")
    var isEnchanted: Boolean = false

    /** Дистанция применения умения | Skill cast range */
    var range: Int = 0

    /** Является ли умение аурой (переключаемым) | Whether the skill is an aura (toggle) */
    @SerialName("is_aura")
    var isAura: Boolean = false
}