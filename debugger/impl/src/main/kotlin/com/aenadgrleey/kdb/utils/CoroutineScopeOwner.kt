package com.aenadgrleey.kdb.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class CoroutineScopeOwner(
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : Releasable {
    protected val scope = CoroutineScope(dispatcher)

    open fun onRelease() = Unit

    override fun releaseResources() {
        onRelease()
        scope.cancel()
    }
}

interface Releasable {
    fun releaseResources()
}
