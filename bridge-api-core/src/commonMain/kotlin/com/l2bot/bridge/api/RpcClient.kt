package com.l2bot.bridge.api

import com.l2bot.bridge.models.rpc.L2RpcException
import com.l2bot.bridge.protocol.Transport
import com.l2bot.bridge.models.rpc.RpcRequest
import com.l2bot.bridge.models.rpc.RpcResponse
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer

/**
 * Thread-safe RPC client for communicating with the Delphi backend via Transport.
 */
class RpcClient(
    @PublishedApi internal val transport: Transport
) {
    @PublishedApi internal val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @PublishedApi internal val commandMutex = Mutex()

    @PublishedApi internal var requestIdCounter = 0L

    var onGlobalError: ((L2RpcException) -> Unit)? = null

    @PublishedApi
    internal fun nextId(): Long = synchronized(this) { ++requestIdCounter }

    /**
     * Executes a remote procedure call.
     * @param method The name of the remote method to invoke.
     * @param params Arguments for the method (defaults to empty object to avoid Delphi typecast errors).
     * @param timeoutMs Maximum time to wait for a response.
     * @return Deserialized object of type [T].
     */
    suspend inline fun <reified T> call(
        method: String,
        params: JsonElement? = JsonObject(emptyMap()),
        timeoutMs: Long = 20000
    ): T {
        val id = nextId()
        val currentSerializer = serializer<T>()

        return commandMutex.withLock {
            val request = RpcRequest(id, method, params)
            val requestJson = json.encodeToString(request)

            try {
                transport.send(requestJson)
            } catch (e: Exception) {
                handleError(L2RpcException.Transport("Transport error in method $method", e))
            }

            val response = readMatchingResponse(id, timeoutMs, method)
            processResponse(response, currentSerializer)
        }
    }

    /**
     * Reads responses from transport in a loop until one with the expected ID arrives.
     * Stale responses from previously timed-out requests are silently discarded.
     */
    @PublishedApi
    internal suspend fun readMatchingResponse(id: Long, timeoutMs: Long, method: String): RpcResponse {
        val deadline = System.currentTimeMillis() + timeoutMs
        while (true) {
            val remaining = deadline - System.currentTimeMillis()
            if (remaining <= 0) {
                handleError(L2RpcException.Transport("Response timeout (${timeoutMs}ms) in method $method"))
            }
            val line = try {
                transport.receive(remaining)
            } catch (e: Exception) {
                handleError(L2RpcException.Transport("Transport error in method $method", e))
            } ?: handleError(L2RpcException.Transport("Response timeout (${timeoutMs}ms) in method $method"))

            val candidate = try {
                json.decodeFromString<RpcResponse>(line)
            } catch (e: Exception) {
                handleError(L2RpcException.Serialization("Malformed JSON from Delphi: $line", e))
            }

            if (candidate.id == id) {
                return candidate
            }
            // stale response from a previously timed-out request — discard, loop
        }
    }

    /**
     * Validates the response and maps it to the target data model.
     */
    @PublishedApi
    internal fun <T> processResponse(
        response: RpcResponse,
        serializer: KSerializer<T>
    ): T {
        if (response.status == "error") {
            val err = response.error
            handleError(L2RpcException.RemoteError(err?.code ?: -1, err?.message ?: "Unknown"))
        }
        val resultElement = response.result
            ?: handleError(L2RpcException.Serialization("Result field is missing in successful response"))

        return try {
            json.decodeFromJsonElement(serializer, resultElement)
        } catch (e: Exception) {
            handleError(L2RpcException.Serialization("Failed to map result to ${serializer.descriptor.serialName}", e))
        }
    }

    /**
     * Triggers the global error hook and throws the exception to halt the coroutine.
     */
    @PublishedApi
    internal fun handleError(ex: L2RpcException): Nothing {
        onGlobalError?.invoke(ex)
        throw ex
    }
}