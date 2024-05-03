package com.aenadgrleey.kdb.setter.model

import java.nio.file.Path

sealed interface Breakpoint {
    val name: String
    val filePath: Path
}
