package com.l2bot.bridge.api

import com.l2bot.bridge.core.L2AdrenalineImpl

interface L2Adrenaline {

    suspend fun getAvailableBots(): List<L2Bot>

    suspend fun connectToBot(charName: String): L2Bot

    companion object {
        operator fun invoke(transportProvider: TransportProvider): L2Adrenaline {
            return L2AdrenalineImpl(transportProvider)
        }
    }
}
