package com.l2bot.bridge.models.item

import com.l2bot.bridge.models.interfaces.IL2Item
import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Data class for inventory items.
 * Data class для предметов инвентаря.
 */
@Serializable
data class L2Item(
    // IL2Object fields
    override val name: String = "",
    override val id: Int = 0,
    override val oid: Int = 0,
    override val valid: Boolean = false,
    @SerialName("l2_class")
    override val l2Class: L2Class = L2Class.UNKNOWN,

    // IL2Item fields
    /** Item slot in inventory | Слот в инвентаре */
    override val slot: Int = 0,
    /** Item count | Количество */
    override val count: Long = 0,
    /** Is equipped | Надет ли */
    override val equipped: Boolean = false,
    /** Item type | Тип предмета */
    @SerialName("item_type")
    override val itemType: Int = 0,
    /** Grade (numeric) | Грейд */
    override val grade: Int = 0,
    /** Grade name | Название грейда */
    @SerialName("grade_name")
    override val gradeName: String = "",
    /** Is named item | Именной предмет */
    @SerialName("is_named")
    override val isNamed: Boolean = false,
    /** Body part slot | Слот экипировки */
    @SerialName("body_part")
    override val bodyPart: Int = 0,
    /** Enchant level | Уровень заточки */
    override val enchant: Int = 0,
    /** First augment ID | ID первого аугмента */
    @SerialName("augment_id")
    override val augmentId: Int = 0,
    /** Second augment ID | ID второго аугмента */
    @SerialName("augment_id2")
    override val augmentId2: Int = 0,
    /** Remaining time | Оставшееся время */
    @SerialName("remain_time")
    override val remainTime: Int = 0,
    /** Attack element type | Элемент атаки */
    @SerialName("atk_elem")
    override val atkElem: Int = 0,
    /** Attack element power | Сила элемента */
    @SerialName("elem_power")
    override val elemPower: Int = 0,
    /** Water resistance | Защита от воды */
    @SerialName("p_water")
    override val waterPower: Int = 0,
    /** Fire resistance | Защита от огня */
    @SerialName("p_fire")
    override val firePower: Int = 0,
    /** Earth resistance | Защита от земли */
    @SerialName("p_earth")
    override val earthPower: Int = 0,
    /** Wind resistance | Защита от ветра */
    @SerialName("p_wind")
    override val windPower: Int = 0,
    /** Unholy resistance | Защита от тьмы */
    @SerialName("p_unholy")
    override val unholyPower: Int = 0,
    /** Holy resistance | Защита от святости */
    @SerialName("p_holy")
    override val holyPower: Int = 0
) : IL2Item
