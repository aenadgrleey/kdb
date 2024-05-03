@file:OptIn(ExperimentalKDBApi::class)

package com.aenadgrleey.kdb.executor

import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.debugger.model.DebuggerTool
import com.aenadgrleey.kdb.tracker.SessionTracker
import com.aenadgrleey.kdb.utils.CoroutineScopeOwner
import com.aenadgrleey.kdb.utils.ExperimentalKDBApi
import com.aenadgrleey.kdb.utils.onReady
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class ProcessOwnerImpl(
    private val config: Config,
    private val sessionTracker: SessionTracker
) : ProcessOwner, CoroutineScopeOwner() {
    private val process = CompletableDeferred<Process>()

    override fun startProcess() {
        scope.launch {
            val initCommand = when (config.tool) {
                DebuggerTool.GDB -> GDB_INIT
                DebuggerTool.LLDB -> LLDB_INIT
            }
            val builder = ProcessBuilder()
                .command(initCommand)
//                .redirectOutput(sessionTracker.output)

            process.complete(builder.start())
        }
    }

    override suspend fun executeCommand(command: String) {
        process.onReady {
            /*no-op*/
        }
    }

    private companion object {
        const val GDB_INIT = "gdb --interpreter=mi"
        const val LLDB_INIT = "lldb-mi"
    }
}
