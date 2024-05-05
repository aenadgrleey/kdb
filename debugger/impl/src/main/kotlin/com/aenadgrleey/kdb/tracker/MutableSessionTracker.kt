package com.aenadgrleey.kdb.tracker

interface MutableSessionTracker : SessionTracker {
    fun trackDebuggerCommand(track: String)
    fun trackDebuggerOutput(track: String)
    fun trackConsoleInput(track: String)
    fun trackConsoleOutput(track: String)
}
