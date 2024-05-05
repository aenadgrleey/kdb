package com.aenadgrleey.kdb.async.parsers

import com.aenadgrleey.kdb.async.DebugEventParser
import com.aenadgrleey.kdb.executor.model.BreakpointHit
import com.aenadgrleey.kdb.setter.BreakpointSetter
import com.aenadgrleey.kdb.utils.RecordClasses
import com.aenadgrleey.kdb.utils.STOP_REASON_ARG
import com.aenadgrleey.kdb.utils.isAsyncRecord
import com.aenadgrleey.kdb.utils.matchesRecordClass
import com.aenadgrleey.kdb.utils.toResultMap
import me.tatarka.inject.annotations.Inject

@Inject
class BreakpointHitParser(
    private val breakpointSetter: BreakpointSetter
) : DebugEventParser {

    override fun parse(input: String): BreakpointHit? {
        if (
            !input.isAsyncRecord() ||
            !input.matchesRecordClass(RecordClasses.STOPPED)
        ) {
            return null
        }

        val resultMap = input.toResultMap()

        val stopReason = resultMap[STOP_REASON_ARG]
        if (stopReason != BREAKPOINT_HIT) return null

        val breakpointNumberStr = resultMap[BREAKPOINT_NUMBER] as? String
        val breakpointNumberInt = breakpointNumberStr?.toIntOrNull() ?: return null

        val breakpoint = breakpointSetter.getBreakpoint(breakpointNumberInt) ?: return null

        return BreakpointHit(breakpoint)
    }

    companion object {
        const val BREAKPOINT_HIT = "breakpoint-hit"
        const val BREAKPOINT_NUMBER = "bkptno"
    }
}
