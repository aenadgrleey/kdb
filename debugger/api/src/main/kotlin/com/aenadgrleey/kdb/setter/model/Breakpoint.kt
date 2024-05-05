@file:Suppress("")

package com.aenadgrleey.kdb.setter.model

import java.nio.file.Path
import kotlin.properties.Delegates

data class Breakpoint(
    val number: Int,
    val line: Int,
    val filePath: Path,
)

class SettingBreakpoint {
    constructor()

    constructor(line: Int, filePath: Path) : this() {
        this.line = line
        this.filePath = filePath
    }

    var line by Delegates.notNull<Int>()
    var filePath by Delegates.notNull<Path>()

    @Suppress("RedundantRequireNotNullCall")
    fun check() {
        checkNotNull(line) { "Please specify line of the breakpoint" }
        checkNotNull(filePath) { "Please specify path of the file" }
    }
}
