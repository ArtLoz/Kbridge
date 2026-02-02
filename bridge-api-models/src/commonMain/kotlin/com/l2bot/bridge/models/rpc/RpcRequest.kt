package com.l2bot.bridge.models.rpc

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * RPC запрос к Delphi (JSON-RPC 2.0)
 */
@Serializable
data class RpcRequest(
    val id: Long,
    val method: String,
    val params: JsonElement? = null
)
