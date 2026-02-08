package com.l2bot.bridge.models.gps

import kotlinx.serialization.Serializable

@Serializable
data class GpsPoint(
    val id: Int,
    val x: Double,
    val y: Double,
    val z: Double,
    val name: String = "",
    val radius: Double = 0.0
)
