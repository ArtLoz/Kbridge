package com.l2bot.bridge.api

import com.l2bot.bridge.protocol.Transport

interface TransportProvider {
    suspend fun scanAvailableBots(): List<String>
    fun createTransport(): Transport
}
