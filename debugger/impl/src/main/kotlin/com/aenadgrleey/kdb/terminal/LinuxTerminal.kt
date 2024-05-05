package com.aenadgrleey.kdb.terminal

open class LinuxTerminal : Terminal {
    override val sudo: String = "sudo"
    override val make: String = "make"
    override val removeFile: String = "rm"
    override val grantPermission: List<String> = listOf("chmod", "+x")
}
