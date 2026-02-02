package com.l2bot.bridge.transport.jvm

import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.WinBase

class PipeScanner {
    
    fun scanAvailableBots(): List<String> {
        return scanForPipes()
    }
    
    private fun scanForPipes(): List<String> {
        val foundBots = mutableListOf<String>()
        val findData = WinBase.WIN32_FIND_DATA()
        val pipeMask = "\\\\.\\pipe\\*"
        val handle = Kernel32.INSTANCE.FindFirstFile(pipeMask, findData.pointer)
        
        if (handle == WinBase.INVALID_HANDLE_VALUE) {
            return emptyList()
        }
        
        try {
            do {
                findData.read()
                val pipeName = findData.fileName
                if (pipeName.startsWith("l2bot_commands_")) {
                    val charName = pipeName.removePrefix("l2bot_commands_")
                    foundBots.add(charName)
                }
            } while (Kernel32.INSTANCE.FindNextFile(handle, findData.pointer))
        } finally {
            Kernel32.INSTANCE.FindClose(handle)
        }

        return foundBots
    }
}
