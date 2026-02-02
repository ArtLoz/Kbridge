package com.l2bot.bridge.models.skill

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс умений, доступных для изучения | Interface for skills available for learning
 */
@Serializable
data class LearnItem(
    /** Уровень персонажа, необходимый для изучения умения | Character level required to learn the skill */
    @SerialName("need_level")
    var needLevel: Int,

    /** Максимально возможный уровень этого умения | Maximum possible level of this skill */
    @SerialName("max_level")
    var maxLevel: Int,

    /** Стоимость изучения умения в SP | Skill learning cost in SP */
    @SerialName("sp_cost")
    var spCost: Long,

    /** * Количество дополнительных требований (предметов) для изучения
     * Number of additional requirements (items) for learning
     */
    var requirements: Int,

    /** Группа умения | Skill group */
    var group: Int,
) : L2Skill() {

}