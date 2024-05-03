package com.aenadgrleey.kdb.debugger

import com.aenadgrleey.kdb.di.DebuggerScope
import com.aenadgrleey.kdb.executor.Executor
import com.aenadgrleey.kdb.setter.BreakpointSetter
import com.aenadgrleey.kdb.stepper.Stepper
import com.aenadgrleey.kdb.tracker.SessionTracker
import me.tatarka.inject.annotations.Inject

@Inject
@DebuggerScope
class DebuggerImpl(
    setter: BreakpointSetter,
    executor: Executor,
    stepper: Stepper,
    tracker: SessionTracker,
) : Debugger,
    BreakpointSetter by setter,
    Executor by executor,
    Stepper by stepper,
    SessionTracker by tracker
