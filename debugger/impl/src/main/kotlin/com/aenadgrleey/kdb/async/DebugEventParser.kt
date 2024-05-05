package com.aenadgrleey.kdb.async

import com.aenadgrleey.kdb.executor.model.DebugEvent
import com.aenadgrleey.kdb.result.ResultParser

interface DebugEventParser : ResultParser<DebugEvent?>
