package com.aenadgrleey.kdb.process

import com.aenadgrleey.kdb.async.parsers.BreakpointHitParser
import com.aenadgrleey.kdb.utils.isAsyncRecord
import com.aenadgrleey.kdb.utils.isResultRecord
import com.aenadgrleey.kdb.utils.toResultMap

fun String.parseBreakpointNumber(): Int? {
    if (isResultRecord()) {
        val resultMap = toResultMap()
        val numberStr = resultMap[BreakpointHitParser.BREAKPOINT_NUMBER] as? String
        val numberInt = numberStr?.toIntOrNull()
        return numberInt
    }
    return null
}

fun String.isInputBreakpointHit(number: Int): Boolean {
    val resultMap = toResultMap()
    return (isAsyncRecord() && resultMap[BreakpointHitParser.BREAKPOINT_NUMBER] == number)
}
