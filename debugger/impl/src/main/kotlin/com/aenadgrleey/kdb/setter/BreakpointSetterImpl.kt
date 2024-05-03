package com.aenadgrleey.kdb.setter

import com.aenadgrleey.kdb.utils.loggedRunCatching

class BreakpointSetterImpl : BreakpointSetter {
    override fun setBreakpoint(): Result<Unit> = loggedRunCatching { }

    override fun setWatchpoint(): Result<Unit> = loggedRunCatching { }
}
