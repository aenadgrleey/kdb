package com.aenadgrleey.kdb.setter

import com.aenadgrleey.kdb.setter.model.Breakpoint
import com.aenadgrleey.kdb.setter.model.Watchpoint

interface BreakpointSetter {
    fun setBreakpoint(breakpoint: Breakpoint): Result<Unit>
    fun setWatchpoint(watchpoint: Watchpoint): Result<Unit>
}
