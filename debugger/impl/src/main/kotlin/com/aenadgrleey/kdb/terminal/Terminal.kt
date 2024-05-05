package com.aenadgrleey.kdb.terminal

interface Terminal {
    val sudo: String
    val make: String
    val removeFile: String
    val grantPermission: List<String>
}
