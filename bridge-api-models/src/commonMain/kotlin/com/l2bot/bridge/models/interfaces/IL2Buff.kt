package com.l2bot.bridge.models.interfaces

/**
 * Interface for buffs and debuffs.
 * Интерфейс для баффов и дебаффов.
 */
interface IL2Buff : IL2Object {
    /** Buff level | Уровень баффа */
    val level: Int
    /** Buff sub-level (enchant) | Подуровень (заточка) */
    val level2: Int
    /** Start time (GetTickCount) | Время начала */
    val startTime: Long
    /** End time (GetTickCount) | Время окончания */
    val endTime: Long
    /** Total reuse time | Время отката */
    val reuseTime: Int
}
