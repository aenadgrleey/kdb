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
    enum class Event { Read, Access, Triggered, OutOfScope }
}

data object ExecutionStart : DebugEvent

data class ExecutionBreak(
    val errorCode: Int?
) : DebugEvent

data object ExecutionFinished : DebugEvent
