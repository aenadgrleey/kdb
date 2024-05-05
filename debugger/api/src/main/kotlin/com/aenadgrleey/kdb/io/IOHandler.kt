package com.aenadgrleey.kdb.io

import com.aenadgrleey.kdb.utils.InlineFun
import kotlinx.coroutines.flow.Flow

interface IOHandler {
    val ioEvents: Flow<IOEvent>
    suspend fun inputMessage(input: String): Result<Unit>
}

@InlineFun
suspend fun IOHandler.inputMessage(block: () -> String) = inputMessage(block())
