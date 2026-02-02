package com.l2bot.bridge.models.interfaces

/**
 * Interface for items in NPC shop.
 * Интерфейс для предметов в магазине НПЦ.
 */
interface IL2ShopItem : IL2Item {
    /** Item price | Цена предмета */
    val price: Long
}
