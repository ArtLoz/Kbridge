package com.l2bot.bridge.models.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class L2Class {
    @SerialName("error")
    ERROR,

    @SerialName("drop")
    DROP,

    @SerialName("npc")
    NPC,

    @SerialName("pet")
    PET,

    @SerialName("char")
    CHAR,

    @SerialName("user")
    USER,

    @SerialName("buff")
    BUFF,

    @SerialName("skill")
    SKILL,

    @SerialName("item")
    ITEM,

    @SerialName("unknown")
    UNKNOWN
}