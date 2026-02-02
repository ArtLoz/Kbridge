package com.l2bot.bridge.models.item

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс предметов в магазине NPC | Interface for items in an NPC shop
 */
@Serializable
data class L2ShopItem(
    /** * Цена предмета в магазине | Item price in the shop
     * Обычно указывается в Аденах или другой валюте сервера
     */
    var price: Long = 0
) : L2Item() {

}