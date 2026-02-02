package com.l2bot.bridge.models.interfaces

/**
 * Interface for auction items.
 * Интерфейс для предметов на аукционе.
 */
interface IL2AucItem : IL2Item {
    /** Lot type | Тип лота */
    val lotType: Int
    /** Seller name | Имя продавца */
    val seller: String
    /** Buyout price | Цена выкупа */
    val price: Long
    /** Duration in days | Срок размещения в днях */
    val days: Int
    /** Time until auction ends | Время до окончания торгов */
    val endTime: Long
}
