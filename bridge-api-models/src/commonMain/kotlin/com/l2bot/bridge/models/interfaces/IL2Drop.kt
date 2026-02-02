package com.l2bot.bridge.models.interfaces

/**
 * Interface for items on the ground (drop).
 * Интерфейс для предметов на земле (дроп).
 */
interface IL2Drop : IL2Spawn {
    /** Item count | Количество предметов */
    val count: Long
    /** Owner's OID | OID владельца */
    val ownerOid: Int
    /** Is stackable | Стопкуемый ли */
    val stackable: Boolean
    /** Belongs to us | Принадлежит нам */
    val isMy: Boolean
}
