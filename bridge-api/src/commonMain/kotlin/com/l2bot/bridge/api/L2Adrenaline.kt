package com.l2bot.bridge.api

interface L2Adrenaline {

    suspend fun getAvailableBots(): List<L2Bot>

    suspend fun connectToBot(charName: String): L2Bot
}
