package com.aenadgrleey.kdb.executor

import com.aenadgrleey.kdb.async.DebugEventParser
import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.executor.model.DebugEvent
import com.aenadgrleey.kdb.process.ProcessOwner
import com.aenadgrleey.kdb.process.command
import com.aenadgrleey.kdb.terminal.Terminal
import com.aenadgrleey.kdb.utils.CoroutineScopeOwner
import com.aenadgrleey.kdb.utils.absolutePathString
import com.aenadgrleey.kdb.utils.loggedRunCatching
import com.aenadgrleey.kdb.utils.space
import com.aenadgrleey.kdb.utils.terminalCommand
import com.aenadgrleey.kdb.utils.with
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking
import me.tatarka.inject.annotations.Inject
import kotlin.io.path.div
import kotlin.io.path.exists

@Inject
class ExecutorImpl(
    private val config: Config,
    private val processOwner: ProcessOwner,
    private val terminal: Terminal,
    private val eventParser: DebugEventParser
) : Executor, CoroutineScopeOwner(Dispatchers.IO) {

    private val cachePath = config.makeOutDir / config.target
    private var useCached: Boolean = cachePath.exists()

    override var isExecuting: Boolean = false
    override val executionJob: Job get() = processOwner.processJob

    override val eventFlow: Flow<DebugEvent> = processOwner
        .asyncEvents
        .mapNotNull { eventParser.parse(it) }

    override suspend fun startDebugger(): Result<Unit> =
        loggedRunCatching {
            if (!useCached) compileExecutable()
            processOwner.startProcess()
            processOwner.command { ATTACH_COMMAND space cachePath.absolutePathString }
            processOwner.command { RUN_COMMAND }
        }

    override suspend fun continueExecution(): Result<Unit> = loggedRunCatching {
        processOwner.command { CONTINUE_COMMAND }
    }

    override suspend fun stopDebugger(): Result<Unit> =
        loggedRunCatching {
            processOwner.command { DETACH_COMMAND }
        }

    override suspend fun resetCompileCache(): Result<Boolean> =
        loggedRunCatching {
            if (cachePath.exists()) {
                terminalCommand(
                    verbose = true
                ) { terminal.sudo with terminal.removeFile with cachePath.absolutePathString }
            }
            useCached.also { useCached = false }
        }

    private suspend fun compileExecutable() {
        terminalCommand(
            workingDir = config.workingDir
        ) { terminal.make with config.target }
        terminalCommand(
            workingDir = config.workingDir
        ) { terminal.grantPermission with cachePath.absolutePathString }
        useCached = true
    }

    override fun onRelease() {
        runBlocking { processOwner.command { EXIT_COMMAND } }
    }

    internal companion object {
        const val RUN_COMMAND = "-exec-run"
        const val ATTACH_COMMAND = "-file-exec-and-symbols"
        const val DETACH_COMMAND = "-target-detach"
        const val EXIT_COMMAND = "-gdb-exit"
        const val CONTINUE_COMMAND = "-exec-continue"
    }
}
