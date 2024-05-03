package com.aenadgrleey.kdb

import com.aenadgrleey.kdb.debugger.model.DebuggerTool

fun main() {
    val debugger = Debugger(DebuggerTool.GDB)
    debugger.setBreakpoint()
}
