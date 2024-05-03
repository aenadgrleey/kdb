package com.aenadgrleey.kdb.debugger

import com.aenadgrleey.kdb.Stepper
import com.aenadgrleey.kdb.executor.Executor
import com.aenadgrleey.kdb.setter.BreakpointSetter
import me.tatarka.inject.annotations.Inject

@Inject
class DebuggerImpl(
    setter: BreakpointSetter,
    executor: Executor,
    stepper: Stepper,
) : Debugger,
    BreakpointSetter by setter,
    Executor by executor,
    Stepper by stepper
