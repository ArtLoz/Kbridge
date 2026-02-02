package com.l2bot.bridge.models.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс NPC и питомцев | NPC and Pet interface
 */
@Serializable
open class L2Npc : L2Live() {
    /** Является ли NPC питомцем или суммоном | Whether the NPC is a pet or a summon */
    @SerialName("is_pet")
    var isPet: Boolean = false

    /** Тип питомца | Pet type */
    @SerialName("pet_type")
    var petType: Int = 0

    /** * Объект владельца (для питомцев и суммонов) | Owner object (for pets and summons)
     * Может быть null, если владельца нет или он вне зоны видимости
     */
    var owner: L2Live? = null

    /** * Тип NPC (0-Monster, 1-Guard, 2-Merchant, и т.д.) | NPC type
     */
    @SerialName("npc_type")
    var npcType: Int = 0

    /** Количество NPC в группе | NPC count in a group */
    @SerialName("npc_count")
    var npcCount: Int = 0
}