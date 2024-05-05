package com.aenadgrleey.kdb.utils

sealed interface LineSeparator {
    val symbol: String
    val designation: String

    data object CR : LineSeparator {
        override val symbol: String = "\r"
        override val designation: String = "CR"
    }

    data object CRLF : LineSeparator {
        override val symbol: String = "\r\n"
        override val designation: String = "CR-LF"
    }

    companion object {
        val Default = CRLF
    }
}
