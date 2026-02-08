package com.l2bot.bridge.core

import com.l2bot.bridge.api.L2Adrenaline
import com.l2bot.bridge.api.L2Bot
import com.l2bot.bridge.api.TransportProvider

fun L2Adrenaline(transportProvider: TransportProvider): L2Adrenaline {
    return L2AdrenalineImpl(transportProvider)
}

internal class L2AdrenalineImpl(
    private val transportProvider: TransportProvider
) : L2Adrenaline {

    override suspend fun getAvailableBots(): List<L2Bot> {
        val charNames = transportProvider.scanAvailableBots()
        return charNames.map { charName ->
            val transport = transportProvider.createTransport()
            L2BotImpl(transport, charName)
        }
    }

    override suspend fun connectToBot(charName: String): L2Bot {
        val transport = transportProvider.createTransport()
        val bot = L2BotImpl(transport, charName)
        bot.connect()
        return bot
    }
}
