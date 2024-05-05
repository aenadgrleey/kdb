package com.aenadgrleey.kdb.utils

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi

@InlineFun
suspend inline fun <T, R> Deferred<T>.onReady(block: T.() -> R): R {
    val value = await()
    return block(value)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Deferred<T>.getOrNull(): T? =
    if (isCompleted) getCompleted() else null
