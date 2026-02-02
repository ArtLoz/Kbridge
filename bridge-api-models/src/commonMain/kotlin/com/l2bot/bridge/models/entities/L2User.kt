package com.l2bot.bridge.models.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


/**
 * Интерфейс нашего персонажа | Our character interface
 */
@Serializable
data class L2User(
    /** Возможность кристаллизации | Crystallization ability */
    @SerialName("can_cryst")
    val canCryst: Boolean,

    /** Количество зарядов | Number of charges */
    val charges: Int,

    /** Количество душ | Number of souls */
    val souls: Int,

    /** Штраф веса | Weight penalty */
    @SerialName("weight_penalty")
    val weightPenalty: Int,

    /** Штраф оружия | Weapon penalty */
    @SerialName("weapon_penalty")
    val weaponPenalty: Int,

    /** Штраф брони | Armor penalty */
    @SerialName("armor_penalty")
    val armorPenalty: Int,

    /** Штраф смерти | Death penalty */
    @SerialName("death_penalty")
    val deathPenalty: Int,
    @SerialName("stat_str") val str: Int,
    @SerialName("stat_dex") val dex: Int,
    @SerialName("stat_con") val con: Int,
    @SerialName("stat_int") val int: Int,
    @SerialName("stat_wit") val wit: Int,
    @SerialName("stat_men") val men: Int,
    @SerialName("p_atk") val pAtk: Int,
    @SerialName("p_atk_spd") val pAtkSpd: Int,
    @SerialName("p_def") val pDef: Int,
    @SerialName("p_accuracy") val pAccuracy: Int,
    @SerialName("p_evasion") val pEvasion: Int,
    @SerialName("p_crit") val pCrit: Int,

    @SerialName("m_atk") val mAtk: Int,
    @SerialName("m_def") val mDef: Int,
    @SerialName("m_accuracy") val mAccuracy: Int,
    @SerialName("m_evasion") val mEvasion: Int,
    @SerialName("m_crit") val mCrit: Int,
) : L2Char() {
}