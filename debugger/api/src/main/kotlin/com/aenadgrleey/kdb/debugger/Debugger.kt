package com.aenadgrleey.kdb.debugger

import com.aenadgrleey.kdb.executor.Executor
import com.aenadgrleey.kdb.setter.BreakpointSetter
import com.aenadgrleey.kdb.stepper.Stepper
import com.aenadgrleey.kdb.tracker.SessionTracker

interface Debugger :
    BreakpointSetter,
    Stepper,
    Executor,
    SessionTracker
