package com.l2bot.bridge.models

import com.l2bot.bridge.models.entities.L2Char
import com.l2bot.bridge.models.entities.L2Npc
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Интерфейс группы (Party) | Party interface
 */
@Serializable
class L2Party {
    /** OID лидера группы | Party leader's OID */
    @SerialName("leader_oid")
    var leaderOid: Int = 0

    /** * Тип распределения трофеев | Loot distribution type
     * (0-Finders, 1-Random, 2-Random+IncludeSpcl, 3-ByTurn, 4-ByTurn+IncludeSpcl)
     */
    @SerialName("loot_type")
    var lootType: Int = 0

    /** Наличие активного приглашения в группу | Presence of an active party invite */
    @SerialName("is_ask_join")
    var isAskJoin: Boolean = false

    /** Имя приглашающего в группу | Name of the person inviting to party */
    @SerialName("ask_join_name")
    var askJoinName: String = ""

    /** Время ожидания ответа на приглашение | Party invite wait time */
    @SerialName("ask_join_time")
    var askJoinTime: Long = 0

    /** Объект лидера группы | Party leader object */
    var leader: L2Char? = null

    /** Список членов группы | List of party members */
    var members: List<L2Char> = emptyList()

    /** Список питомцев группы | List of party pets */
    var pets: List<L2Npc> = emptyList()
}