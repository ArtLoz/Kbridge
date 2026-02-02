package com.l2bot.bridge.models.types

enum class ChatType(val id: Int) {
    GENERAL(0),
    SHOUT(1),
    WHISPER(2),
    PARTY(3),
    CLAN(4),
    TRADE(8),
    ALLIANCE(9),
    ANNOUNCEMENT(10),
    HERO(17),
    COMMAND_CHANNEL(20),
    WORLD(25)
}