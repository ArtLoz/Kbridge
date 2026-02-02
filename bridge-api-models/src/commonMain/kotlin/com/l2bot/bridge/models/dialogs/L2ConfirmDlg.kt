package com.l2bot.bridge.models.dialogs

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс окна подтверждения (Confirm Dialog) | Confirmation dialog interface
 */
@Serializable
data class L2ConfirmDlg(
    /** * ID сообщения из системного чата | Message ID from system chat
     * Позволяет понять текст вопроса по базе знаний игры
     */
    @SerialName("msg_id")
    val msgId: Int,

    /** Уникальный ID запроса для отправки ответа (Yes/No) | Unique request ID for the answer */
    @SerialName("req_id")
    val reqId: Int,

    /** Имя отправителя запроса (NPC или ник игрока) | Sender's name */
    val sender: String,

    /** Время до автоматического закрытия окна (в миллисекундах или тиках) | Time until auto-close */
    @SerialName("end_time")
    val endTime: Long,
)