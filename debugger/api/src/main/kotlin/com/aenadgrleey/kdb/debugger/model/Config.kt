package com.aenadgrleey.kdb.debugger.model

import java.nio.file.Path

data class Config(
    val tool: DebuggerTool,
    val workingDir: Path,
    val makefile: Path,
    val executablePath: Path
)
