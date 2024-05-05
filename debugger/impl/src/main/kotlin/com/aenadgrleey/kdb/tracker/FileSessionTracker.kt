package com.aenadgrleey.kdb.tracker

import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.utils.ReleasableImpl
import com.aenadgrleey.kdb.utils.input
import com.aenadgrleey.kdb.utils.isConsoleOutput
import me.tatarka.inject.annotations.Inject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import kotlin.io.path.div

@Inject
class FileSessionTracker(config: Config) : MutableSessionTracker, ReleasableImpl() {

    override val output: File = (config.workingDir / "output.txt").toFile()
    private val writer: BufferedWriter = BufferedWriter(FileWriter(output))

    init {
        writer.input("Starting session track, kdb config - $config")
    }

    override fun trackDebuggerCommand(track: String) {
        writer.input("debugger input record: $track")
    }

    override fun trackDebuggerOutput(track: String) {
        if (track.isConsoleOutput()) {
            trackConsoleOutput(track)
        } else {
            writer.input("debugger output record: $track")
        }
    }

    override fun trackConsoleInput(track: String) {
        writer.input("console input: $track")
    }

    override fun trackConsoleOutput(track: String) {
        writer.input("console output: $track")
    }

    override fun onRelease() {
        writer.close()
    }
}
