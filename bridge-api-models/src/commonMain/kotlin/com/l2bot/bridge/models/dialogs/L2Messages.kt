package com.l2bot.bridge.models.dialogs

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Менеджер сообщений чата | Chat messages manager
 */
@Serializable
data class L2Messages(
    /** Максимальное количество сообщений в истории | Max messages in history */
    @SerialName("max_count")
    val maxCount: Int,

    /** Список сообщений | List of messages */
    val items: List<L2ChatMessage>,
)