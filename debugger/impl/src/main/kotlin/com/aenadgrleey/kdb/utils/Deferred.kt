package com.aenadgrleey.kdb.utils

import kotlinx.coroutines.Deferred

@InlineFun
suspend inline fun <T> Deferred<T>.onReady(block: (T) -> Unit): T {
    val value = await()
    block(value)
    return value
}
