package com.l2bot.bridge.transport.jvm

import com.l2bot.bridge.protocol.Transport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JvmTransportProvider : com.l2bot.bridge.api.TransportProvider {
    
    private val scanner = PipeScanner()
    
    override suspend fun scanAvailableBots(): List<String> = withContext(Dispatchers.IO) {
        scanner.scanAvailableBots()
    }
    
    override fun createTransport(): Transport {
        return JvmPipeTransport()
    }
}
