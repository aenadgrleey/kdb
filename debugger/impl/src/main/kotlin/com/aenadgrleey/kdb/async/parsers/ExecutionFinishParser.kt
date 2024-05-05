package com.aenadgrleey.kdb.async.parsers

import com.aenadgrleey.kdb.async.DebugEventParser
import com.aenadgrleey.kdb.executor.model.ExecutionFinished
import com.aenadgrleey.kdb.utils.RecordClasses
import com.aenadgrleey.kdb.utils.STOP_REASON_ARG
import com.aenadgrleey.kdb.utils.isAsyncRecord
import com.aenadgrleey.kdb.utils.matchesRecordClass
import com.aenadgrleey.kdb.utils.toResultMap
import me.tatarka.inject.annotations.Inject

@Inject
class ExecutionFinishParser : DebugEventParser {
    override fun parse(input: String): ExecutionFinished? {
        if (
            !input.isAsyncRecord() ||
            !input.matchesRecordClass(RecordClasses.STOPPED)
        ) {
            return null
        }

        val resultMap = input.toResultMap()
        if (resultMap[STOP_REASON_ARG] != EXITED_NORMALLY) {
            return null
        }

        return ExecutionFinished
    }

    companion object {
        private const val EXITED_NORMALLY = "exited-normally"
    }
}
