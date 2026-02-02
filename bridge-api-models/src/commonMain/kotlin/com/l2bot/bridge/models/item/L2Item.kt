package com.l2bot.bridge.models.item

import com.l2bot.bridge.models.L2Object
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
open class L2Item : L2Object() {
    /** Слот предмета в инвентаре | Item slot in inventory */
    var slot: Int = 0

    /** Количество предметов в стопке | Item count in stack */
    var count: Long = 0

    /** Надет ли предмет | Is item equipped */
    var equipped: Boolean = false

    /** Тип предмета | Item type (e.g. Armor, Weapon, etc.) */
    @SerialName("item_type")
    var itemType: Int = 0

    /** Грейд предмета (цифровое значение) | Item grade (numeric) */
    var grade: Int = 0

    /** Название грейда (None, D, C, B, A, S, R...) | Grade name */
    @SerialName("grade_name")
    var gradeName: String = ""

    /** Является ли предмет именным | Is item named */
    @SerialName("is_named")
    var isNamed: Boolean = false

    /** Слот экипировки (часть тела) | Equipment slot (body part) */
    @SerialName("body_part")
    var bodyPart: Int = 0

    /** Уровень заточки | Enchant level */
    var enchant: Int = 0

    /** ID первого аугмента (ЛС) | First augmentation ID */
    @SerialName("augment_id")
    var augmentId: Int = 0

    /** ID второго аугмента (ЛС) | Second augmentation ID */
    @SerialName("augment_id2")
    var augmentId2: Int = 0

    /** Оставшееся время (для временных предметов) в мс | Remaining time for shadow/temporary items */
    @SerialName("remain_time")
    var remainTime: Int = 0

    /** Элемент атаки | Attack element type */
    @SerialName("atk_elem")
    var atkElem: Int = 0

    /** Мощность атаки элементом | Attack element power */
    @SerialName("elem_power")
    var elemPower: Int = 0

    /** Защита от воды | Water resistance power */
    @SerialName("p_water")
    var waterPower: Int = 0

    /** Защита от огня | Fire resistance power */
    @SerialName("p_fire")
    var firePower: Int = 0

    /** Защита от земли | Earth resistance power */
    @SerialName("p_earth")
    var earthPower: Int = 0

    /** Защита от ветра | Wind resistance power */
    @SerialName("p_wind")
    var windPower: Int = 0

    /** Защита от тьмы | Unholy resistance power */
    @SerialName("p_unholy")
    var unholyPower: Int = 0

    /** Защита от святости | Holy resistance power */
    @SerialName("p_holy")
    var holyPower: Int = 0
}