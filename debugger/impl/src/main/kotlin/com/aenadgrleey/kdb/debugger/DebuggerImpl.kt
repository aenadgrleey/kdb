package com.aenadgrleey.kdb.debugger

import com.aenadgrleey.kdb.di.DebuggerScope
import com.aenadgrleey.kdb.executor.Executor
import com.aenadgrleey.kdb.io.IOHandler
import com.aenadgrleey.kdb.process.ProcessOwner
import com.aenadgrleey.kdb.setter.BreakpointSetter
import com.aenadgrleey.kdb.tracker.SessionTracker
import me.tatarka.inject.annotations.Inject

@Inject
@DebuggerScope
class DebuggerImpl(
    private val setter: BreakpointSetter,
    private val executor: Executor,
    private val tracker: SessionTracker,
    private val ioHandler: IOHandler,
    private val processOwner: ProcessOwner,
) : Debugger,
    BreakpointSetter by setter,
    Executor by executor,
    SessionTracker by tracker,
    IOHandler by ioHandler {

    override fun releaseResources() {
        tracker.releaseResources()
        executor.releaseResources()
        processOwner.releaseResources()
    }
}
