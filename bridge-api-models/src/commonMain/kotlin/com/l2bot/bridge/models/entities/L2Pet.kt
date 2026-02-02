package com.l2bot.bridge.models.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс питомца | Pet interface
 */
@Serializable
data class L2Pet(
    /** * Уровень сытости питомца | Pet's hunger (fed) level
     * Значение от 0 до 100 (или выше в зависимости от хроник)
     */
    val fed: Int
) : L2Npc() {}