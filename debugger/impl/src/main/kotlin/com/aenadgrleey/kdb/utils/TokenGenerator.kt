package com.aenadgrleey.kdb.utils

object TokenGenerator {

    private var lastToken = 0

    fun newToken(): String {
        return (++lastToken).toString()
    }
}

fun token() = TokenGenerator.newToken()
