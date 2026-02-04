package com.l2bot.bridge.transport.jvm

import com.l2bot.bridge.protocol.Transport
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.WinNT
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.nio.charset.StandardCharsets

class JvmPipeTransport : Transport {
    
    private var commandPipe: WinNT.HANDLE? = null
    private var responsePipe: WinNT.HANDLE? = null
    private var actionPipe: WinNT.HANDLE? = null
    private var packetPipe: WinNT.HANDLE? = null
    private var cliPacketPipe: WinNT.HANDLE? = null

    private val responseBuffer = StringBuilder()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    private val _isConnected = MutableStateFlow(false)
    override val isConnected: Flow<Boolean> = _isConnected.asStateFlow()
    
    override suspend fun connect(target: String) = withContext(Dispatchers.IO) {
        responseBuffer.clear()
        try {
            commandPipe = connectToPipe(
                "\\\\.\\pipe\\l2bot_commands_$target",
                Kernel32.GENERIC_WRITE
            ) ?: throw Exception("Failed to connect to commands pipe")
            
            responsePipe = connectToPipe(
                "\\\\.\\pipe\\l2bot_responses_$target",
                Kernel32.GENERIC_READ
            ) ?: throw Exception("Failed to connect to responses pipe")
            
            actionPipe = connectToPipe(
                "\\\\.\\pipe\\l2bot_actions_$target",
                Kernel32.GENERIC_READ
            ) ?: throw Exception("Failed to connect to actions pipe")
            
            packetPipe = connectToPipe(
                "\\\\.\\pipe\\l2bot_packets_$target",
                Kernel32.GENERIC_READ
            ) ?: throw Exception("Failed to connect to packets pipe")
            
            cliPacketPipe = connectToPipe(
                "\\\\.\\pipe\\l2bot_clipackets_$target",
                Kernel32.GENERIC_READ
            ) ?: throw Exception("Failed to connect to clipackets pipe")
            
            _isConnected.value = true
        } catch (e: Exception) {
            _isConnected.value = false
            throw e
        }
    }
    
    override suspend fun disconnect() = withContext(Dispatchers.IO) {
        responseBuffer.clear()
        scope.cancel()
        commandPipe?.let { Kernel32.INSTANCE.CloseHandle(it) }
        responsePipe?.let { Kernel32.INSTANCE.CloseHandle(it) }
        actionPipe?.let { Kernel32.INSTANCE.CloseHandle(it) }
        packetPipe?.let { Kernel32.INSTANCE.CloseHandle(it) }
        cliPacketPipe?.let { Kernel32.INSTANCE.CloseHandle(it) }
        commandPipe = null
        responsePipe = null
        actionPipe = null
        packetPipe = null
        cliPacketPipe = null
        _isConnected.value = false
    }
    
    override suspend fun send(data: String) = withContext(Dispatchers.IO) {
        val cmdPipe = commandPipe ?: throw TransportException("Command pipe not connected")
        val bytes = data.toByteArray(StandardCharsets.UTF_8)
        if (!cmdPipe.write(bytes)) {
            val error = Kernel32.INSTANCE.GetLastError()
            _isConnected.value = false
            throw TransportException("Failed to write to pipe (error: $error)")
        }
    }

    override suspend fun receive(timeoutMs: Long): String? = withContext(Dispatchers.IO) {
        val respPipe = responsePipe ?: throw TransportException("Response pipe not connected")
        respPipe.readLineWithTimeout(timeoutMs, responseBuffer)
    }
    
    override fun receiveActions(): Flow<String> = flow {
        val pipe = requireNotNull(actionPipe) { "Not connected" }
        
        while (currentCoroutineContext().isActive) {
            try {
                val line = pipe.readLine()
                if (line != null) {
                    emit(line)
                } else {
                    _isConnected.value = false
                    break
                }
            } catch (e: Exception) {
                _isConnected.value = false
                break
            }
        }
    }.flowOn(Dispatchers.IO)
    
    override fun receivePackets(): Flow<String> = flow {
        val pipe = requireNotNull(packetPipe) { "Not connected" }
        
        while (currentCoroutineContext().isActive) {
            try {
                val line = pipe.readLine()
                if (line != null) {
                    emit(line)
                } else {
                    _isConnected.value = false
                    break
                }
            } catch (e: Exception) {
                _isConnected.value = false
                break
            }
        }
    }.flowOn(Dispatchers.IO)
    
    override fun receiveCliPackets(): Flow<String> = flow {
        val pipe = requireNotNull(cliPacketPipe) { "Not connected" }
        
        while (currentCoroutineContext().isActive) {
            try {
                val line = pipe.readLine()
                if (line != null) {
                    emit(line)
                } else {
                    _isConnected.value = false
                    break
                }
            } catch (e: Exception) {
                _isConnected.value = false
                break
            }
        }
    }.flowOn(Dispatchers.IO)
}

class TransportException(message: String, cause: Throwable? = null) : Exception(message, cause)
