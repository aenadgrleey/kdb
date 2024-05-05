package com.aenadgrleey.kdb.utils

abstract class ReleasableImpl : Releasable {
    protected open fun onRelease() = Unit
    override fun releaseResources() = onRelease()
}
