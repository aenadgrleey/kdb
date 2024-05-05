package com.aenadgrleey.kdb.utils

import java.io.BufferedWriter

fun BufferedWriter.input(input: String) {
    write(input)
    newLine()
    flush()
}
