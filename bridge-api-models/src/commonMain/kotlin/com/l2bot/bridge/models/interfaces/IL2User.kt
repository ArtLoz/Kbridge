package com.l2bot.bridge.models.interfaces

/**
 * Interface for user's own character.
 * Интерфейс для собственного персонажа.
 */
interface IL2User : IL2Char {
    /** Crystallization ability | Возможность кристаллизации */
    val canCryst: Boolean
    /** Number of charges | Количество зарядов */
    val charges: Int
    /** Number of souls | Количество душ */
    val souls: Int
    /** Weight penalty | Штраф веса */
    val weightPenalty: Int
    /** Weapon penalty | Штраф оружия */
    val weaponPenalty: Int
    /** Armor penalty | Штраф брони */
    val armorPenalty: Int
    /** Death penalty | Штраф смерти */
    val deathPenalty: Int
    /** STR stat | Параметр STR */
    val str: Int
    /** DEX stat | Параметр DEX */
    val dex: Int
    /** CON stat | Параметр CON */
    val con: Int
    /** INT stat | Параметр INT */
    val int: Int
    /** WIT stat | Параметр WIT */
    val wit: Int
    /** MEN stat | Параметр MEN */
    val men: Int
    /** Physical attack | Физическая атака */
    val pAtk: Int
    /** Physical attack speed | Скорость физической атаки */
    val pAtkSpd: Int
    /** Physical defense | Физическая защита */
    val pDef: Int
    /** Physical accuracy | Физическая точность */
    val pAccuracy: Int
    /** Physical evasion | Физическое уклонение */
    val pEvasion: Int
    /** Physical critical rate | Физический крит */
    val pCrit: Int
    /** Magical attack | Магическая атака */
    val mAtk: Int
    /** Magical defense | Магическая защита */
    val mDef: Int
    /** Magical accuracy | Магическая точность */
    val mAccuracy: Int
    /** Magical evasion | Магическое уклонение */
    val mEvasion: Int
    /** Magical critical rate | Магический крит */
    val mCrit: Int
}
