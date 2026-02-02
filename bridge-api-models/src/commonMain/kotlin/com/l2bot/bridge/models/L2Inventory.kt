package com.l2bot.bridge.models

import com.l2bot.bridge.models.item.L2Item
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс инвентаря персонажа | Character inventory interface
 */
@Serializable
class L2Inventory {
    /** Список предметов персонажа | User's item list */
    var user: List<L2Item> = emptyList()

    /** Список предметов питомца | Pet's item list */
    var pet: List<L2Item> = emptyList()

    /** Список квестовых предметов | Quest item list */
    var quest: List<L2Item> = emptyList()
}