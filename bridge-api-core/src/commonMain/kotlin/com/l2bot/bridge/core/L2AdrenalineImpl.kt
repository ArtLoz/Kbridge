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

    private val botCache = mutableMapOf<String, L2Bot>()

    override suspend fun getAvailableBots(): List<L2Bot> {
        val charNames = transportProvider.scanAvailableBots()
        val currentNames = charNames.toSet()

        // Remove bots that are no longer available
        botCache.keys.removeAll { it !in currentNames }

        // Add new bots, keep existing instances
        for (charName in charNames) {
            botCache.getOrPut(charName) {
                L2BotImpl(transportProvider.createTransport(), charName)
            }
        }

        return charNames.map { botCache.getValue(it) }
    }

    override suspend fun connectToBot(charName: String): L2Bot {
        return botCache.getOrPut(charName) {
            L2BotImpl(transportProvider.createTransport(), charName)
        }.also { (it as L2BotImpl).connect() }
    }
}
