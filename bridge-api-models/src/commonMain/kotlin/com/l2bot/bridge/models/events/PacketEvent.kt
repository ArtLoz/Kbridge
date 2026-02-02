package com.l2bot.bridge.models.events

/**
 * Пакет сервер → клиент (l2bot_packets)
 * @param header Заголовок пакета (hex)
 * @param data Данные пакета (hex)
 */
data class PacketEvent(
    val header: String,
    val data: String
)
