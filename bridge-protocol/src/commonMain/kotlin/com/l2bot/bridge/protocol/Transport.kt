package com.l2bot.bridge.protocol

import kotlinx.coroutines.flow.Flow

interface Transport {
    suspend fun send(data: String)
    suspend fun receive(timeoutMs: Long): String?

    fun receiveActions(): Flow<String>
    fun receivePackets(): Flow<String>
    fun receiveCliPackets(): Flow<String>
    
    suspend fun connect(target: String)
    suspend fun disconnect()
    
    val isConnected: Flow<Boolean>
}
