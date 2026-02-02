package com.l2bot.bridge.models

import kotlinx.serialization.Serializable

@Serializable
data class L2GPSPoint (
    val name: String,
    val x:Int,
    val y:Int,
    val z:Int,
    val id:Int
)