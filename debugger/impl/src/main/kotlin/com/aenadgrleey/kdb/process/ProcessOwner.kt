package com.aenadgrleey.kdb.process

import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.debugger.model.DebuggerTool
import com.aenadgrleey.kdb.executor.ExecutorImpl
import com.aenadgrleey.kdb.tracker.MutableSessionTracker
import com.aenadgrleey.kdb.utils.CONSOLE_INPUT_REQUEST
import com.aenadgrleey.kdb.utils.CoroutineScopeOwner
import com.aenadgrleey.kdb.utils.InlineFun
import com.aenadgrleey.kdb.utils.LineSeparator
import com.aenadgrleey.kdb.utils.firstResultRecord
import com.aenadgrleey.kdb.utils.getOrNull
import com.aenadgrleey.kdb.utils.input
import com.aenadgrleey.kdb.utils.isAsyncRecord
import com.aenadgrleey.kdb.utils.isConsoleInputRequest
import com.aenadgrleey.kdb.utils.isConsoleOutput
import com.aenadgrleey.kdb.utils.onReady
import com.aenadgrleey.kdb.utils.token
import com.aenadgrleey.kdb.utils.unreachable
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

@Inject
class ProcessOwner(
    private val config: Config,
    private val sessionTracker: MutableSessionTracker
) : CoroutineScopeOwner() {

    private val lineSeparator = LineSeparator.Default

    private val process = CompletableDeferred<Process>()
    val processJob: Job
        get() = scope.coroutineContext[Job] ?: unreachable()

    private var mProcessWriter: BufferedWriter? = null
    private val processWriter: BufferedWriter
        get() = mProcessWriter ?: error("Tried to access writer before process init")

    private val processOutput = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1
    )

    val asyncEvents: Flow<String>
        get() = processOutput.filter(String::isAsyncRecord)
    val consoleEvents: Flow<String>
        get() = processOutput.filter { it.isConsoleOutput() || it.isConsoleInputRequest() }

    init {
        setInputBreakpoint()
    }

    fun startProcess() {
        scope.launch {
            val builder: ProcessBuilder = when (config.tool) {
                DebuggerTool.GDB -> ProcessBuilder(GDB_INIT)
                DebuggerTool.LLDB -> ProcessBuilder(LLDB_INIT)
            }

            builder
                .directory(config.workingDir.toFile())
                .redirectErrorStream(true)

            val startedProcess = builder.start()

            val outputWriter = OutputStreamWriter(startedProcess.outputStream)
            mProcessWriter = BufferedWriter(outputWriter)

            val inputStreamReader = InputStreamReader(startedProcess.inputStream)
            val processReader = BufferedReader(inputStreamReader)

            launch {
                processReader.use { reader ->
                    var outputLine: String
                    do {
                        outputLine = reader.readLine() ?: break
                        ensureActive()
                        sessionTracker.trackDebuggerOutput(outputLine)
                        val number = inputBreakpointNumber
                        if (number != null && outputLine.isInputBreakpointHit(number)) {
                            processOutput.emit(CONSOLE_INPUT_REQUEST)
                            command { ExecutorImpl.CONTINUE_COMMAND }
                        } else {
                            processOutput.emit(outputLine)
                        }
                    } while (true)
                }
            }

            process.complete(startedProcess)
            startedProcess.waitFor()
        }
    }

    suspend fun command(command: String): String {
        return process.onReady {
            val token = token()
            val builtCommand = "$token$command ${lineSeparator.designation}"
            processWriter.input(builtCommand)
            sessionTracker.trackDebuggerCommand(builtCommand)
            processOutput.firstResultRecord(token)
        }
    }

    suspend fun userInput(input: String) {
        return process.onReady {
            processWriter.input(input)
            sessionTracker.trackConsoleInput(input)
        }
    }

    override fun releaseResources() {
        val process = process.getOrNull()
        process?.destroyForcibly()
        mProcessWriter?.close()
    }

    private var inputBreakpointNumber: Int? = null
    private fun setInputBreakpoint() {
        scope.launch {
            inputBreakpointNumber = command { "-break-insert std::istream::getline" }.parseBreakpointNumber()
        }
    }

    private companion object {
        val GDB_INIT = listOf("gdb", "--interpreter=mi")
        const val LLDB_INIT = "lldb-mi"
    }
}

@InlineFun
suspend inline fun ProcessOwner.command(command: () -> String) = command(command())
