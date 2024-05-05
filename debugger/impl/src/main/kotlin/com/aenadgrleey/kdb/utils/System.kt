package com.aenadgrleey.kdb.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.io.path.absolutePathString
import kotlin.time.Duration

fun workingDir(): Path {
    val workingDirString =
        System.getProperty("user.dir") ?: wtf()
    val workingDirPath =
        Path.of(workingDirString) ?: wtf()
    return workingDirPath
}

val Path.absolutePathString: String
    get() = absolutePathString()

@InlineFun
suspend inline fun terminalCommand(
    workingDir: Path = workingDir(),
    verbose: Boolean = false,
    timeout: Duration? = null,
    crossinline create: () -> List<String>
) = terminalCommand(create(), workingDir, verbose, timeout)

suspend fun terminalCommand(
    command: List<String>,
    workingDir: Path = workingDir(),
    verbose: Boolean = false,
    timeout: Duration? = null,
) {
    withContext(Dispatchers.IO) {
        val process = ProcessBuilder(command)
            .directory(workingDir.toFile())
            .start()

        if (verbose) {
            val inputStreamReader = InputStreamReader(process.inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            bufferedReader.readLines().forEach { println(it) }
        }

        val resultCode = if (timeout != null) {
            process.waitFor(
                timeout.inWholeMilliseconds,
                TimeUnit.MILLISECONDS
            )
            process.exitValue()
        } else {
            process.waitFor()
        }

        if (resultCode != 0) {
            throw BadProcessEndException(resultCode)
        }
    }
}
