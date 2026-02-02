package com.l2bot.bridge.models

import com.l2bot.bridge.models.item.L2Item
import kotlinx.serialization.Serializable

/**
 * Интерфейс инвентаря персонажа | Character inventory interface
 */
@Serializable
data class L2Inventory(
    /** Список предметов персонажа | User's item list */
    val user: List<L2Item> = emptyList(),

    /** Список предметов питомца | Pet's item list */
    val pet: List<L2Item> = emptyList(),

    /** Список квестовых предметов | Quest item list */
    val quest: List<L2Item> = emptyList()
)
