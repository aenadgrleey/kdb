package com.aenadgrleey.kdb.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class CoroutineScopeOwner(
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ReleasableImpl() {
    protected val scope = CoroutineScope(dispatcher)
    val job: Job get() = scope.coroutineContext[Job] ?: unreachable()

    override fun onRelease() {
        scope.cancel()
    }
}
