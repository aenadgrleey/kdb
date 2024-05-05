@file:Suppress("LongParameterList")

package com.aenadgrleey.kdb

import com.aenadgrleey.kdb.debugger.Debugger
import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.debugger.model.DebuggerTool
import com.aenadgrleey.kdb.debugger.model.OSType
import com.aenadgrleey.kdb.debugger.model.Output
import com.aenadgrleey.kdb.debugger.model.current
import com.aenadgrleey.kdb.di.DebuggerComponent
import com.aenadgrleey.kdb.di.create
import com.aenadgrleey.kdb.utils.InlineFun
import com.aenadgrleey.kdb.utils.workingDir
import java.nio.file.Path

fun Debugger(config: Config): Debugger {
    val debuggerComponent = DebuggerComponent.create(config)
    return debuggerComponent.debugger
}

// to hide di
@InlineFun
inline fun Debugger(
    config: Config,
    block: Debugger.() -> Unit
): Debugger = Debugger(config).apply(block)

@InlineFun
fun Debugger(
    makefile: Path,
    target: String,
    tool: DebuggerTool = DebuggerTool.GDB,
    workingDir: Path = workingDir(),
    output: Output = Output.Empty,
    makeOutDir: Path = workingDir,
    block: Debugger.() -> Unit = {}
): Debugger {
    return Debugger(
        Config(
            tool = tool,
            workingDir = workingDir,
            makefile = makefile,
            target = target,
            output = output,
            osType = OSType.current,
            makeOutDir = makeOutDir,
        )
    ).apply(block)
}
