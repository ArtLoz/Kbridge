package com.l2bot.bridge.models.interfaces

/**
 * Interface for inventory items.
 * Интерфейс для предметов инвентаря.
 */
interface IL2Item : IL2Object {
    /** Slot in inventory | Слот в инвентаре */
    val slot: Int
    /** Item count | Количество */
    val count: Long
    /** Is equipped | Надет ли */
    val equipped: Boolean
    /** Item type | Тип предмета */
    val itemType: Int
    /** Grade (numeric) | Грейд (число) */
    val grade: Int
    /** Grade name | Название грейда */
    val gradeName: String
    /** Is named item | Именной предмет */
    val isNamed: Boolean
    /** Body part slot | Слот экипировки */
    val bodyPart: Int
    /** Enchant level | Уровень заточки */
    val enchant: Int
    /** First augment ID | ID первого аугмента */
    val augmentId: Int
    /** Second augment ID | ID второго аугмента */
    val augmentId2: Int
    /** Remaining time for temp items | Оставшееся время */
    val remainTime: Int
    /** Attack element type | Элемент атаки */
    val atkElem: Int
    /** Attack element power | Сила элемента атаки */
    val elemPower: Int
    /** Water resistance | Защита от воды */
    val waterPower: Int
    /** Fire resistance | Защита от огня */
    val firePower: Int
    /** Earth resistance | Защита от земли */
    val earthPower: Int
    /** Wind resistance | Защита от ветра */
    val windPower: Int
    /** Unholy resistance | Защита от тьмы */
    val unholyPower: Int
    /** Holy resistance | Защита от святости */
    val holyPower: Int
}
