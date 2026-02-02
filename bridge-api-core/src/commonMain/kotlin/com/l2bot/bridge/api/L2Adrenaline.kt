package com.l2bot.bridge.api

interface TransportProvider {
    suspend fun scanAvailableBots(): List<String>
    fun createTransport(): com.l2bot.bridge.protocol.Transport
}

object L2Adrenaline {
    private var transportProvider: TransportProvider? = null
    
    fun setTransportProvider(provider: TransportProvider) {
        transportProvider = provider
    }
    
    suspend fun getAvailableBots(): List<L2Bot> {
        val provider = requireNotNull(transportProvider) { 
            "TransportProvider not set. Call L2Adrenaline.setTransportProvider() first" 
        }
        
        val charNames = provider.scanAvailableBots()
        return charNames.map { charName ->
            val transport = provider.createTransport()
            L2Bot(transport, charName)
        }
    }
    
    suspend fun connectToBot(charName: String): L2Bot {
        val provider = requireNotNull(transportProvider) {
            "TransportProvider not set. Call L2Adrenaline.setTransportProvider() first"
        }
        
        val transport = provider.createTransport()
        val bot = L2Bot(transport, charName)
        bot.connect()
        return bot
    }
}
