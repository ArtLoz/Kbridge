package com.l2bot.bridge.models.dialogs

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс сообщения чата | Chat message interface
 */
@Serializable
data class L2ChatMessage(
    /** OID отправителя сообщения | Sender's OID */
    val oid: Int,

    /** Время получения сообщения | Message timestamp */
    val time: Long,

    /** Имя отправителя | Sender's name */
    val sender: String,

    /** Текст сообщения | Message text */
    val text: String,

    /** * Тип чата (канал) | Chat type (channel)
     * 0-All, 1-Shout, 2-Tell, 3-Party, 4-Clan, 7-Trade, и т.д.
     * Подробнее: https://adrenalinebot.com/ru/api/adrenaline/Constants/TMessageType
     */
    @SerialName("chat_type")
    val chatType: Int,

    /** Прочитано ли сообщение | Whether the message is unread */
    val unread: Boolean,
)