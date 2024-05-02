package com.aenadgrleey.kdb

import com.aenadgrleey.kdb.debugger.Debugger
import com.aenadgrleey.kdb.debugger.DebuggerImpl
import com.aenadgrleey.kdb.model.Config
import com.aenadgrleey.kdb.model.DebuggerTool
import com.aenadgrleey.kdb.setter.BreakpointSetterImpl

fun Debugger(config: Config): Debugger {
    return DebuggerImpl(BreakpointSetterImpl())
}

fun Debugger(tool: DebuggerTool): Debugger {
    return Debugger(Config(tool))
}
