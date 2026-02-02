package com.l2bot.bridge.models.interfaces

/**
 * Interface for skills available for learning.
 * Интерфейс для умений, доступных для изучения.
 */
interface IL2LearnItem : IL2Skill {
    /** Required level | Необходимый уровень */
    val needLevel: Int
    /** Maximum skill level | Максимальный уровень умения */
    val maxLevel: Int
    /** SP cost | Стоимость в SP */
    val spCost: Long
    /** Number of requirements | Количество требований */
    val requirements: Int
    /** Skill group | Группа умения */
    val group: Int
}
