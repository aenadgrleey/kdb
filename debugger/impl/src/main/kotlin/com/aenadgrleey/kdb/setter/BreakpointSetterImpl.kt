package com.aenadgrleey.kdb.setter

import com.aenadgrleey.kdb.setter.model.Breakpoint
import com.aenadgrleey.kdb.setter.model.Watchpoint
import com.aenadgrleey.kdb.utils.loggedRunCatching
import me.tatarka.inject.annotations.Inject

@Inject
class BreakpointSetterImpl : BreakpointSetter {
    override fun setBreakpoint(breakpoint: Breakpoint) =
        loggedRunCatching {}

    override fun setWatchpoint(watchpoint: Watchpoint) =
        loggedRunCatching {}
}
