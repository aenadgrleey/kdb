package com.aenadgrleey.kdb

import com.aenadgrleey.kdb.debugger.Debugger
import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.debugger.model.DebuggerTool
import com.aenadgrleey.kdb.di.DebuggerComponent
import com.aenadgrleey.kdb.di.create
import com.aenadgrleey.kdb.utils.InlineFun

fun Debugger(config: Config): Debugger {
    val debuggerComponent = DebuggerComponent.create(config)
    return debuggerComponent.debugger
}

@InlineFun
inline fun Debugger(
    config: Config,
    block: Debugger.() -> Unit
): Debugger = Debugger(config).apply(block)

fun Debugger(tool: DebuggerTool): Debugger {
    return Debugger(Config(tool))
}

@InlineFun
inline fun Debugger(
    tool: DebuggerTool,
    block: Debugger.() -> Unit
): Debugger = Debugger(tool).apply(block)
