package com.l2bot.bridge.models.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class L2Race {
    @SerialName("human") HUMAN,
    @SerialName("elf") ELF,
    @SerialName("dark_elf") DARK_ELF,
    @SerialName("orc") ORC,
    @SerialName("dwarf") DWARF,
    @SerialName("kamael") KAMAEL,
    @SerialName("erthea") ERTHEA,
    @SerialName("unknown") UNKNOWN
}