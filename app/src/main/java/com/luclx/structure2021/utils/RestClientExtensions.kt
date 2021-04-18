package com.luclx.structure2021.utils

import kotlinx.coroutines.delay

suspend inline fun <T> executeWithRetry(
    times: Int = 1,
    initialDelay: Long = 100,
    maxDelay: Long = 1000,
    block: () -> Resource<T>
): Resource<T> {
    var currentDelay = initialDelay
    repeat(times - 1) {
        val response = block()
        when (response.status) {
            ResponseStatus.ERROR -> {
                delay(currentDelay)
                currentDelay = currentDelay.coerceAtMost(maxDelay)
            }
            else -> return response
        }
    }
    return block()
}

inline fun <T> handleRequest(
    requestFunc: () -> T
): Resource<T> {
    val responseHandler = ResponseHandler()
    return try {
        responseHandler.handleSuccess(requestFunc.invoke())
    } catch (e: Exception) {
        responseHandler.handleException(e)
    }
}
