package com.aenadgrleey.kdb.async

import com.aenadgrleey.kdb.executor.model.DebugEvent
import me.tatarka.inject.annotations.Inject

@Inject
class DebugEventParserImpl(
    private val parsers: Set<DebugEventParser>
) : DebugEventParser {

    override fun parse(input: String): DebugEvent? {
        parsers.forEach {
            val event = it.parse(input)
            if (event != null) return event
        }
        return null
    }
}
