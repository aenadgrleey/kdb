package com.aenadgrleey.kdb.executor.model

import com.aenadgrleey.kdb.setter.model.Breakpoint
import com.aenadgrleey.kdb.setter.model.Watchpoint

sealed interface DebugEvent

data class BreakpointHit(
    val breakpoint: Breakpoint
) : DebugEvent

data class WatchpointEvent(
    val event: Event,
    val watchpoint: Watchpoint
) : DebugEvent {
    enum class Event { Read, Write }
}

data class ProgramError(
    val errorMessage: String
) : DebugEvent

data class DebugError(
    val errorMessage: String,
    val error: Any? = null
) : DebugEvent
