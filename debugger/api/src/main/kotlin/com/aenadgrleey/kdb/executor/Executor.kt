package com.aenadgrleey.kdb.executor

import com.aenadgrleey.kdb.executor.model.DebugEvent
import com.aenadgrleey.kdb.utils.Releasable
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface Executor : Releasable {
    val isExecuting: Boolean
    val executionJob: Job
    val eventFlow: Flow<DebugEvent>
    suspend fun resetCompileCache(): Result<Boolean>
    suspend fun startDebugger(): Result<Unit>
    suspend fun continueExecution(): Result<Unit>
    suspend fun stopDebugger(): Result<Unit>
}

val Executor.isStopped: Boolean
    get() = !isExecuting
