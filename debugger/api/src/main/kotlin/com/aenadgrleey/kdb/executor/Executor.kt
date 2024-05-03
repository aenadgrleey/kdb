package com.aenadgrleey.kdb.executor

import com.aenadgrleey.kdb.executor.model.DebugEvent
import kotlinx.coroutines.flow.Flow
import java.nio.file.Path

interface Executor {
    fun eventFlow(): Flow<DebugEvent>
    suspend fun run(path: Path): Result<Unit>
}
