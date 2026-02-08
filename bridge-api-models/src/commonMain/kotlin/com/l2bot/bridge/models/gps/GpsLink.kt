package com.l2bot.bridge.models.gps

import kotlinx.serialization.Serializable

@Serializable
data class GpsLink(
    val startId: Int,
    val endId: Int,
    val oneWay: Boolean = false
)
