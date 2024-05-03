package com.aenadgrleey.kdb

import com.aenadgrleey.kdb.debugger.Debugger
import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.debugger.model.DebuggerTool
import com.aenadgrleey.kdb.di.DebuggerComponent
import com.aenadgrleey.kdb.di.create
import com.aenadgrleey.kdb.utils.InlineFun
import com.aenadgrleey.kdb.utils.workingDir
import java.nio.file.Path

fun Debugger(config: Config): Debugger {
    val debuggerComponent = DebuggerComponent.create(config)
    return debuggerComponent.debugger
}

@InlineFun
inline fun Debugger(
    config: Config,
    block: Debugger.() -> Unit
): Debugger = Debugger(config).apply(block)

fun Debugger(
    tool: DebuggerTool = DebuggerTool.GDB,
    workingDir: Path = workingDir()
): Debugger = Debugger(Config(tool, workingDir))

@InlineFun
inline fun Debugger(
    tool: DebuggerTool = DebuggerTool.GDB,
    workingDir: Path = workingDir(),
    block: Debugger.() -> Unit
): Debugger = Debugger(tool, workingDir).apply(block)
