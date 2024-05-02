package com.aenadgrleey.kdb.debugger

import com.aenadgrleey.kdb.setter.BreakpointSetter

class DebuggerImpl(
    breakpointSetter: BreakpointSetter
) : Debugger,
    BreakpointSetter by breakpointSetter
