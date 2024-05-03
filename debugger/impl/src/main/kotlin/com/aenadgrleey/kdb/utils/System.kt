package com.aenadgrleey.kdb.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
suspend inline fun executeCommand(
    timeout: Duration? = null,
    crossinline create: () -> String
) {
    withContext(Dispatchers.IO) {
        val command = create()
        val process = ProcessBuilder(command)
            .start()

        if (timeout != null) {
            process.waitFor(
                timeout.inWholeMilliseconds,
                TimeUnit.MILLISECONDS
            )
        } else {
            process.waitFor()
        }
    }
}
