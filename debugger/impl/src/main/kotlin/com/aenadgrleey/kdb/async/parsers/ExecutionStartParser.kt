package com.aenadgrleey.kdb.async.parsers

import com.aenadgrleey.kdb.async.DebugEventParser
import com.aenadgrleey.kdb.executor.model.ExecutionStart
import com.aenadgrleey.kdb.utils.RecordClasses
import com.aenadgrleey.kdb.utils.isAsyncRecord
import com.aenadgrleey.kdb.utils.matchesRecordClass
import me.tatarka.inject.annotations.Inject

@Inject
class ExecutionStartParser : DebugEventParser {
    override fun parse(input: String): ExecutionStart? {
        if (
            input.isAsyncRecord() &&
            input.matchesRecordClass(RecordClasses.RUNNING)
        ) {
            return ExecutionStart
        }

        return null
    }
}
