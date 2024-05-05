package com.aenadgrleey.kdb.utils

fun wtf(): Nothing = error("unexpected behaviour")

fun unreachable(): Nothing = error("shouldn't reach this")

class BadProcessEndException(code: Int) :
    Exception("process didn't finish normally, quit with code $code")
