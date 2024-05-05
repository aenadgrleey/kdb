package com.aenadgrleey.kdb

import com.aenadgrleey.kdb.debugger.model.DebuggerTool
import com.aenadgrleey.kdb.debugger.model.Output
import com.aenadgrleey.kdb.io.IOEvent
import com.aenadgrleey.kdb.io.inputMessage
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.io.path.Path
import kotlin.io.path.div

fun main() {
    val projectDir = Path(System.getProperty("user.dir"))
    val workingDir = projectDir / "sample" / "cpp"
    val debugger = Debugger(
        makefile = workingDir / "Makefile",
        workingDir = workingDir,
        target = "main",
        output = Output.File,
        tool = DebuggerTool.LLDB
    )
    runBlocking {
        debugger.resetCompileCache()
            .logAny("resetCache")

        debugger.startDebugger()
            .logAny("start debugger")
        launch {
            debugger.ioEvents.collect {
                when (it) {
                    IOEvent.AwaitingInput -> debugger.inputMessage {
                        val read = readln()
                        println(read)
                        read
                    }
                    is IOEvent.ConsoleOutput -> println(it.message)
                }
            }
        }
        debugger.executionJob.join()
    }
}

fun <T> Result<T>.logAny(operationName: String = "") {
    onSuccess { println("$operationName success! value: $it") }
    onFailure { println("$operationName error! exception: $it") }
}
