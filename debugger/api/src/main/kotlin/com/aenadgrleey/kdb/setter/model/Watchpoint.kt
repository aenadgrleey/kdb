package com.aenadgrleey.kdb.setter.model

data class Watchpoint(
    val number: Int,
    val propertyName: String,
)

class SettingWatchpoint {
    var propertyName: String? = null
    fun check() = Unit
}
