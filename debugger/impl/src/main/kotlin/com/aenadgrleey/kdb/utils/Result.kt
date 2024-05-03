package com.aenadgrleey.kdb.utils

import io.github.aakira.napier.Napier

@InlineFun
@Suppress("TooGenericExceptionCaught")
inline fun <T, R> T.loggedRunCatching(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Napier.e(e) { "The following error occurred while debugger was running:" }
        Result.failure(e)
    }
}

@InlineFun
@Suppress("TooGenericExceptionCaught")
inline fun <R> loggedRunCatching(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Napier.e(e) { "The following error occurred while debugger was running:" }
        Result.failure(e)
    }
}
