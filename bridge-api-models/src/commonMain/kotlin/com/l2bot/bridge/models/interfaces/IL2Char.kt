package com.l2bot.bridge.models.interfaces

import com.l2bot.bridge.models.types.L2Race

/**
 * Interface for player characters.
 * Интерфейс для игровых персонажей.
 */
interface IL2Char : IL2Live {
    /** Current CP | Текущее CP */
    val cp: Long
    /** Current CP (alias) | Текущее CP */
    val curCp: Long
    /** Maximum CP | Максимальное CP */
    val maxCp: Long
    /** Sex (0 - male, 1 - female) | Пол */
    val sex: Int
    /** Race | Раса */
    val race: L2Race
    /** Is Hero | Является ли Героем */
    val isHero: Boolean
    /** Is Noble | Является ли Дворянином */
    val isNoble: Boolean
    /** Premium account status | Премиум аккаунт */
    val premium: Boolean
    /** Current class ID | ID текущего класса */
    val classId: Int
    /** Main class ID | ID основного класса */
    val mainClassId: Int
    /** Current class name | Название текущего класса */
    val className: String
    /** Alternative class name | Альтернативное название класса */
    val classNameAlt: String
    /** Class priority | Приоритет класса */
    val classPriority: Int
    /** Mount type | Тип маунта */
    val mountType: Int
    /** Store type | Тип торговой лавки */
    val storeType: Int
    /** Number of active cubics | Количество кубиков */
    val cubicCount: Int
    /** Recommendations count | Количество рекомендаций */
    val recom: Int
    /** Clan name | Название клана */
    val clanName: String
    /** Ally name | Название альянса */
    val allyName: String
}
