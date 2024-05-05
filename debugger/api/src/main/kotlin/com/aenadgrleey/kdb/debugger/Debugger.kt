package com.aenadgrleey.kdb.debugger

import com.aenadgrleey.kdb.executor.Executor
import com.aenadgrleey.kdb.io.IOHandler
import com.aenadgrleey.kdb.setter.BreakpointSetter
import com.aenadgrleey.kdb.tracker.SessionTracker
import com.aenadgrleey.kdb.utils.Releasable

interface Debugger :
    BreakpointSetter,
    Executor,
    SessionTracker,
    IOHandler,
    Releasable
