package com.l2bot.bridge.models.rpc

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * RPC ответ от Delphi (JSON-RPC 2.0)
 */
@Serializable
data class RpcResponse(
    val id: Long,
    val status: String, // "success" или "error"
    val result: JsonElement? = null,
    val error: RpcError? = null
)

/**
 * Ошибка RPC
 */
@Serializable
data class RpcError(
    val code: Int,
    val message: String
)
