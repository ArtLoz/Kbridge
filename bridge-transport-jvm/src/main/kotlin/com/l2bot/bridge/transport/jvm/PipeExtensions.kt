package com.l2bot.bridge.transport.jvm

import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference
import kotlinx.coroutines.delay

fun connectToPipe(pipeName: String, accessMode: Int): WinNT.HANDLE? {
    val handle = Kernel32.INSTANCE.CreateFile(
        pipeName,
        accessMode,
        0,
        null,
        Kernel32.OPEN_EXISTING,
        0,
        null
    )
    
    return if (handle == Kernel32.INVALID_HANDLE_VALUE) {
        null
    } else {
        handle
    }
}

fun WinNT.HANDLE.write(data: ByteArray): Boolean {
    val bytesWritten = IntByReference()
    return Kernel32.INSTANCE.WriteFile(this, data, data.size, bytesWritten, null)
}

fun WinNT.HANDLE.readLine(): String? {
    val buffer = StringBuilder()
    val readBuffer = ByteArray(1)
    val bytesRead = IntByReference()
    
    while (true) {
        if (!Kernel32.INSTANCE.ReadFile(this, readBuffer, 1, bytesRead, null)) {
            return null
        }
        if (bytesRead.value == 0) return null
        
        val char = readBuffer[0].toInt().toChar()
        if (char == '\n') break
        if (char != '\r') buffer.append(char)
    }
    
    return buffer.toString()
}

suspend fun WinNT.HANDLE.readLineWithTimeout(timeoutMs: Long, buffer: StringBuilder): String? {
    val startTime = System.currentTimeMillis()
    val readBuffer = ByteArray(4096)
    val bytesRead = IntByReference()
    val available = IntByReference()

    while (true) {
        val newlineIdx = buffer.indexOf('\n')
        if (newlineIdx >= 0) {
            val line = buffer.substring(0, newlineIdx).trimEnd('\r')
            buffer.delete(0, newlineIdx + 1)
            return line
        }

        if (System.currentTimeMillis() - startTime >= timeoutMs) {
            return null
        }

        val peekResult = Kernel32.INSTANCE.PeekNamedPipe(this, null, 0, null, available, null)
        if (peekResult && available.value > 0) {
            val readResult = Kernel32.INSTANCE.ReadFile(this, readBuffer, 4096, bytesRead, null)
            if (readResult && bytesRead.value > 0) {
                buffer.append(String(readBuffer, 0, bytesRead.value, Charsets.UTF_8))
            }
        }

        delay(10)
    }
}
