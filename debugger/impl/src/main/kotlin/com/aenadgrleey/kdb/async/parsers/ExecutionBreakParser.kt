package com.aenadgrleey.kdb.async.parsers

import com.aenadgrleey.kdb.async.DebugEventParser
import com.aenadgrleey.kdb.executor.model.ExecutionBreak
import com.aenadgrleey.kdb.utils.RecordClasses
import com.aenadgrleey.kdb.utils.STOP_REASON_ARG
import com.aenadgrleey.kdb.utils.isAsyncRecord
import com.aenadgrleey.kdb.utils.matchesRecordClass
import com.aenadgrleey.kdb.utils.toResultMap
import me.tatarka.inject.annotations.Inject

@Inject
class ExecutionBreakParser : DebugEventParser {
    override fun parse(input: String): ExecutionBreak? {
        if (
            !input.isAsyncRecord() ||
            !input.matchesRecordClass(RecordClasses.STOPPED)
        ) {
            return null
        }

        val resultMap = input.toResultMap()
        if (resultMap[STOP_REASON_ARG] != EXITED) {
            return null
        }
        return ExecutionBreak(resultMap[EXIT_CODE_ARG] as? Int)
    }

    companion object {
        const val EXITED = "exited"
        const val EXIT_CODE_ARG = "exit-code"
    }
}
