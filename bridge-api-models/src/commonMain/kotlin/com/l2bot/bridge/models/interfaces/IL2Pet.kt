package com.l2bot.bridge.models.interfaces

/**
 * Interface for pets.
 * Интерфейс для питомцев.
 */
interface IL2Pet : IL2Npc {
    /** Pet's hunger (fed) level | Уровень сытости питомца */
    val fed: Int
}
