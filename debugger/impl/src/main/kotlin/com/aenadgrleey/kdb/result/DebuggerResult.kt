package com.aenadgrleey.kdb.result

interface ResultParser<T> {
    fun parse(input: String): T
}

interface ComplexResultParser<T, R> {
    fun parse(other: R, input: String): T
}
