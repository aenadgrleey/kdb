package com.aenadgrleey.kdb.executor

import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.executor.model.DebugEvent
import com.aenadgrleey.kdb.utils.loggedRunCatching
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import java.nio.file.Path

@Inject
class ExecutorImpl(
    private val config: Config,
    private val processOwner: ProcessOwner
) : Executor {
    override fun eventFlow(): Flow<DebugEvent> {
        TODO("Not yet implemented")
    }

    override suspend fun run(path: Path): Result<Unit> = loggedRunCatching {
    }

    private companion object {
//        fun runCommand
    }
}
