package com.aenadgrleey.kdb.async.parsers

import com.aenadgrleey.kdb.async.DebugEventParser
import com.aenadgrleey.kdb.executor.model.WatchpointEvent
import com.aenadgrleey.kdb.setter.BreakpointSetter
import com.aenadgrleey.kdb.utils.RecordClasses
import com.aenadgrleey.kdb.utils.STOP_REASON_ARG
import com.aenadgrleey.kdb.utils.isAsyncRecord
import com.aenadgrleey.kdb.utils.matchesRecordClass
import com.aenadgrleey.kdb.utils.toResultMap
import me.tatarka.inject.annotations.Inject

@Inject
class WatchpointHitParser(
    private val breakpointSetter: BreakpointSetter
) : DebugEventParser {
    override fun parse(input: String): WatchpointEvent? {
        if (
            !input.isAsyncRecord() ||
            !input.matchesRecordClass(RecordClasses.STOPPED)
        ) {
            return null
        }
        val resultMap = input.toResultMap()

        val watchpointNumberStr = resultMap[WATCHPOINT_NUMBER_ARG] as? String
        val watchpointNumberInt = watchpointNumberStr?.toIntOrNull() ?: return null
        return when (resultMap[STOP_REASON_ARG]) {
            WATCHPOINT_TRIGGER -> {
                val watchpoint = breakpointSetter.getWatchpoint(watchpointNumberInt)
                watchpoint?.let { WatchpointEvent(WatchpointEvent.Event.Triggered, watchpoint) }
            }

            WATCHPOINT_READ -> {
                val watchpoint = breakpointSetter.getWatchpoint(watchpointNumberInt)
                watchpoint?.let { WatchpointEvent(WatchpointEvent.Event.Read, watchpoint) }
            }

            WATCHPOINT_ACCESS -> {
                val watchpoint = breakpointSetter.getWatchpoint(watchpointNumberInt)
                watchpoint?.let { WatchpointEvent(WatchpointEvent.Event.Access, watchpoint) }
            }

            WATCHPOINT_OUT_OF_SCOPE -> {
                val watchpoint = breakpointSetter.getWatchpoint(watchpointNumberInt)
                watchpoint?.let { WatchpointEvent(WatchpointEvent.Event.OutOfScope, watchpoint) }
            }

            else -> null
        }
    }

    companion object {
        const val WATCHPOINT_TRIGGER = "watchpoint-trigger"
        const val WATCHPOINT_READ = "read-watchpoint-trigger"
        const val WATCHPOINT_ACCESS = "access-watchpoint-trigger"
        const val WATCHPOINT_OUT_OF_SCOPE = "watchpoint-scope"
        const val WATCHPOINT_NUMBER_ARG = "wpt"
    }
}
