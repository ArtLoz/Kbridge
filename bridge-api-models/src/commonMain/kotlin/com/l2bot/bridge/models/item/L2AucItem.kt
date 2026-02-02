package com.l2bot.bridge.models.item

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс предмета на аукционе | Auction item interface
 */
@Serializable
data class L2AucItem(
    /** Тип лота | Lot type */
    @SerialName("lot_type")
    var lotType: Int,

    /** Имя продавца | Seller's name */
    var seller: String,

    /** * Цена выкупа (Buyout price).
     * Если 0, то выкуп невозможен | If 0, buyout is impossible
     */
    var price: Long,

    /** Срок размещения лота в днях | Lot duration in days */
    var days: Int,

    /** Время до завершения торгов (в секундах) | Time until auction ends (seconds) */
    @SerialName("end_time")
    var endTime: Long,
) : L2Item() {

}