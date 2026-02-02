package com.l2bot.bridge.models.rpc

sealed class L2RpcException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class Transport(message: String, cause: Throwable? = null) : L2RpcException("Transport error: $message", cause)
    class RemoteError(val code: Int, message: String) : L2RpcException("Delphi error [$code]: $message")
    class Serialization(message: String, cause: Throwable? = null) : L2RpcException("Mapping error: $message", cause)
    class Protocol(message: String) : L2RpcException("Protocol mismatch: $message")
}