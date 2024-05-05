package com.aenadgrleey.kdb.tracker

import com.aenadgrleey.kdb.utils.Releasable
import java.io.File

interface SessionTracker : Releasable {
    val output: File
}
