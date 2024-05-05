package com.aenadgrleey.kdb.setter

import com.aenadgrleey.kdb.setter.model.Breakpoint
import com.aenadgrleey.kdb.setter.model.SettingBreakpoint
import com.aenadgrleey.kdb.setter.model.SettingWatchpoint
import com.aenadgrleey.kdb.setter.model.Watchpoint
import com.aenadgrleey.kdb.utils.InlineFun

interface BreakpointSetter {
    val breakpoints: List<Breakpoint>
    val watchpoints: List<Watchpoint>
    fun getBreakpoint(number: Int): Breakpoint?
    fun getWatchpoint(number: Int): Watchpoint?
    suspend fun setBreakpoint(breakpoint: SettingBreakpoint): Result<Unit>
    suspend fun setWatchpoint(watchpoint: SettingWatchpoint): Result<Unit>
}

@InlineFun
suspend inline fun BreakpointSetter.setBreakpoint(
    block: SettingBreakpoint.() -> Unit
): Result<Unit> = runCatching {
    val breakpoint = SettingBreakpoint()
    block(breakpoint)
    setBreakpoint(breakpoint).getOrThrow()
}

@InlineFun
suspend inline fun BreakpointSetter.setWatchpoint(
    block: SettingWatchpoint.() -> Unit
): Result<Unit> = runCatching {
    val watchpoint = SettingWatchpoint()
    block(watchpoint)
    setWatchpoint(watchpoint).getOrThrow()
}
