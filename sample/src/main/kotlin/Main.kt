package com.aenadgrleey

import com.aenadgrleey.kdb.Debugger
import com.aenadgrleey.kdb.model.DebuggerTool

fun main() {
    Debugger(DebuggerTool.GDB).setBreakpoint()
}