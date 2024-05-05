package com.aenadgrleey.kdb.setter

import com.aenadgrleey.kdb.process.ProcessOwner
import com.aenadgrleey.kdb.process.command
import com.aenadgrleey.kdb.result.parsers.BreakpointSetParser
import com.aenadgrleey.kdb.setter.model.Breakpoint
import com.aenadgrleey.kdb.setter.model.SettingBreakpoint
import com.aenadgrleey.kdb.setter.model.SettingWatchpoint
import com.aenadgrleey.kdb.setter.model.Watchpoint
import com.aenadgrleey.kdb.utils.absolutePathString
import com.aenadgrleey.kdb.utils.loggedRunCatching
import com.aenadgrleey.kdb.utils.space
import me.tatarka.inject.annotations.Inject

@Inject
class BreakpointSetterImpl(
    private val processOwner: ProcessOwner
) : BreakpointSetter {
    private val mBreakpoints = mutableListOf<Breakpoint>()
    override val breakpoints: List<Breakpoint>
        get() = mBreakpoints

    private val mWatchpoints = mutableListOf<Watchpoint>()
    override val watchpoints: List<Watchpoint>
        get() = mWatchpoints

    override fun getBreakpoint(number: Int) =
        breakpoints.find { it.number == number }

    override fun getWatchpoint(number: Int): Watchpoint? =
        watchpoints.find { it.number == number }

    override suspend fun setBreakpoint(breakpoint: SettingBreakpoint): Result<Unit> =
        loggedRunCatching {
            breakpoint.check()
            BreakpointSetParser.parse(breakpoint) {
                processOwner.command {
                    INSERT_BREAKPOINT_COMMAND space "${breakpoint.filePath.absolutePathString}:${breakpoint.line}"
                }
            }
        }

    override suspend fun setWatchpoint(watchpoint: SettingWatchpoint): Result<Unit> {
        TODO("Not yet implemented")
    }

    companion object {
        const val INSERT_BREAKPOINT_COMMAND = "-break-insert"
    }
}
