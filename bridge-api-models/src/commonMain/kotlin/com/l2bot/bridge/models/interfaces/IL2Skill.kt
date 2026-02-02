package com.l2bot.bridge.models.interfaces

/**
 * Interface for character skills.
 * Интерфейс для умений персонажа.
 */
interface IL2Skill : IL2Buff {
    /** Is passive skill | Пассивное умение */
    val isPassive: Boolean
    /** Is disabled | Заблокировано */
    val isDisabled: Boolean
    /** Is enchanted | Заточено */
    val isEnchanted: Boolean
    /** Cast range | Дистанция применения */
    val range: Int
    /** Is aura (toggle) | Является ли аурой */
    val isAura: Boolean
}
