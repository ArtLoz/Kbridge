package com.l2bot.bridge.models.events

/**
 * Пакет клиент → сервер (l2bot_clipackets)
 * @param header Заголовок пакета (hex)
 * @param data Данные пакета (hex)
 */
data class CliPacketEvent(
    val header: String,
    val data: String
)
