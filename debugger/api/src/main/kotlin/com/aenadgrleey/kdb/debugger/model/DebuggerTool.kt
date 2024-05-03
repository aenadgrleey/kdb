package com.aenadgrleey.kdb.debugger.model

import com.aenadgrleey.kdb.utils.ExperimentalKDBApi

enum class DebuggerTool {
    GDB,

    @ExperimentalKDBApi
    LLDB
}
