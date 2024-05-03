package com.aenadgrleey.kdb.executor

import com.aenadgrleey.kdb.utils.InlineFun

interface ProcessOwner {
    fun startProcess()
    suspend fun executeCommand(command: String)
}

@InlineFun
suspend inline fun ProcessOwner.executeCommand(block: () -> String) =
    executeCommand(block())
