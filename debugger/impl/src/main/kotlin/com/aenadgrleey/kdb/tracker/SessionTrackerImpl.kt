package com.aenadgrleey.kdb.tracker

import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.utils.absolutePathString
import me.tatarka.inject.annotations.Inject
import java.io.File

@Inject
class SessionTrackerImpl(config: Config) : SessionTracker {

    override val output =
        File(config.workingDir.absolutePathString)
}
