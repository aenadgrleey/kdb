package com.aenadgrleey.kdb.io

import com.aenadgrleey.kdb.process.ProcessOwner
import com.aenadgrleey.kdb.utils.isConsoleInputRequest
import com.aenadgrleey.kdb.utils.isConsoleOutput
import com.aenadgrleey.kdb.utils.loggedRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import me.tatarka.inject.annotations.Inject

@Inject
class IOHandlerImpl(
    private val processOwner: ProcessOwner,
) : IOHandler {
    override val ioEvents: Flow<IOEvent> =
        processOwner.consoleEvents
            .mapNotNull {
                when {
                    it.isConsoleOutput() -> IOEvent.ConsoleOutput(it)
                    it.isConsoleInputRequest() -> IOEvent.AwaitingInput
                    else -> null
                }
            }

    override suspend fun inputMessage(input: String) =
        loggedRunCatching {
            processOwner.userInput(input)
        }
}
