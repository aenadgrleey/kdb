package com.aenadgrleey.kdb.io

sealed interface IOEvent {
    data object AwaitingInput : IOEvent
    data class ConsoleOutput(
        val message: String
    ) : IOEvent
}
