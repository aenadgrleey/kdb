@file:Suppress("TooManyFunctions")

package com.aenadgrleey.kdb.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.regex.Pattern

fun StringBuilder.space(): StringBuilder = append(" ")
infix fun String.space(other: String) = "$this $other"

infix fun String.with(other: String): List<String> = listOf(this, other)
infix fun List<String>.with(other: String): List<String> = this + listOf(other)

// for the simplicity we will ignore some async events
fun String.isAsyncRecord(): Boolean =
    matchRecordPrefix("*") // || startsWith("=")

fun String.isLogRecord(): Boolean =
    matchRecordPrefix("&")

fun String.isConsoleOutput(): Boolean =
    matchRecordPrefix("~") || matchRecordPrefix("@")

const val CONSOLE_INPUT_REQUEST = "inputRequest"
fun String.isConsoleInputRequest(): Boolean =
    this == CONSOLE_INPUT_REQUEST

fun String.isResultRecord(): Boolean =
    matchRecordPrefix("^")

suspend fun Flow<String>.firstResultRecord(token: String) =
    first { it.isResultRecord() && it.recordToken() == token }

fun String.recordToken(): String? {
    val tokenSize = indexOfFirst { !it.isDigit() }
    if (tokenSize < 1) return null
    return substring(0, tokenSize)
}

fun String.recordPrefix(): String {
    val prefixIndex = indexOfFirst { !it.isDigit() }
    return getOrNull(prefixIndex).toString()
}

fun String.matchRecordPrefix(shouldMatch: String): Boolean {
    return this.recordPrefix() == shouldMatch
}

fun String.recordClass(): String {
    val prefixIndex = indexOfFirst { !it.isDigit() }
    val start = prefixIndex + 1
    val firstComma = indexOf(',')
    return if (firstComma == -1) {
        substring(start, lastIndex).trim()
    } else {
        substring(start, firstComma - 1)
    }
}

fun String.matchesRecordClass(shouldMatch: String): Boolean {
    return this.recordClass() == shouldMatch
}

fun String.toResultMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>()

    // key-value pairs
    val pairPattern = Pattern.compile("""(\w+)="([^"]*)"""")
    val matcher = pairPattern.matcher(this)

    while (matcher.find()) {
        val key = matcher.group(1)
        val value = matcher.group(2)
        map[key] = value
    }

    // nested objects
    val nestedObjectPattern = Pattern.compile("""(\w+)=\{(.*?)}""")
    val nestedMatcher = nestedObjectPattern.matcher(this)

    while (nestedMatcher.find()) {
        val key = nestedMatcher.group(1)
        val value = nestedMatcher.group(2)
        map[key] = value.toResultMap() // Recursive parsing
    }

    return map
}
