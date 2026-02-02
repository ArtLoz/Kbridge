package com.l2bot.bridge.models.events

/**
 * Статус подключения к боту
 */
enum class ConnectionStatus {
    /** Подключен к боту */
    CONNECTED,
    
    /** Отключен от бота */
    DISCONNECTED,
    
    /** Ошибка подключения */
    ERROR
}
