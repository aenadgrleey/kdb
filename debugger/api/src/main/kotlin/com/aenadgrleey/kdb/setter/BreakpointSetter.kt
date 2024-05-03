package com.aenadgrleey.kdb.setter

interface BreakpointSetter {
    fun setBreakpoint(): Result<Unit>
    fun setWatchpoint(): Result<Unit>
}
