package com.aenadgrleey.kdb.tracker

import java.io.File

class EmptySessionTracker : MutableSessionTracker {
    override val output: File
        get() = error("current debugger doesn't support file output")

    override fun trackDebuggerCommand(track: String) = Unit

    override fun trackDebuggerOutput(track: String) = Unit

    override fun trackConsoleInput(track: String) = Unit

    override fun trackConsoleOutput(track: String) = Unit

    override fun releaseResources() = Unit
}
