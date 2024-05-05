package com.aenadgrleey.kdb.result.parsers

import com.aenadgrleey.kdb.result.ComplexResultParser
import com.aenadgrleey.kdb.setter.model.Breakpoint
import com.aenadgrleey.kdb.setter.model.SettingBreakpoint
import com.aenadgrleey.kdb.utils.InlineFun
import com.aenadgrleey.kdb.utils.RecordClasses
import com.aenadgrleey.kdb.utils.isResultRecord
import com.aenadgrleey.kdb.utils.matchesRecordClass
import com.aenadgrleey.kdb.utils.toResultMap
import com.aenadgrleey.kdb.utils.wtf

class BreakpointSetParser : ComplexResultParser<Breakpoint, SettingBreakpoint> {
    override fun parse(other: SettingBreakpoint, input: String): Breakpoint {
        if (
            !input.isResultRecord() ||
            !input.matchesRecordClass(RecordClasses.DONE)
        ) {
            error("tried to parse wrong record")
        }
        val resultMap = input.toResultMap()
        val numberStr = resultMap[BREAKPOINT_NUMBER_ARG] as? String
        val numberInt = numberStr?.toIntOrNull() ?: wtf()
        return Breakpoint(
            number = numberInt,
            line = other.line,
            filePath = other.filePath
        )
    }

    companion object {
        fun parse(other: SettingBreakpoint, input: String) = BreakpointSetParser().parse(other, input)

        @InlineFun
        inline fun parse(other: SettingBreakpoint, input: () -> String) = Companion.parse(other, input())

        const val BREAKPOINT_NUMBER_ARG = "number"
    }
}
