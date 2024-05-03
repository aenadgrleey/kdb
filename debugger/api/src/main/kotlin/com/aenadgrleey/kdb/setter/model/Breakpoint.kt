package com.aenadgrleey.kdb.setter.model

import java.nio.file.Path

data class Breakpoint(
    val name: String,
    val filePath: Path
)

data class Watchpoint(
    val propertyName: String,
    val value: Any?
)
