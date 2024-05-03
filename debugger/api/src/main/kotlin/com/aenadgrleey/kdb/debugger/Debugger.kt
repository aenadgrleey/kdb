package com.aenadgrleey.kdb.debugger

import com.aenadgrleey.kdb.Stepper
import com.aenadgrleey.kdb.executor.Executor
import com.aenadgrleey.kdb.setter.BreakpointSetter

interface Debugger : BreakpointSetter, Stepper, Executor
