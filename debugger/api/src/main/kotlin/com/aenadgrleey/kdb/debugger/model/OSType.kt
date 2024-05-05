package com.aenadgrleey.kdb.debugger.model

enum class OSType {
    Linux, Win, Mac;

    companion object
}

val OSType.Companion.current: OSType
    get() {
        val systemName = System.getProperty("os.name")
        return when {
            systemName
                .contains(
                    other = "win",
                    ignoreCase = true
                ) -> OSType.Win

            systemName
                .contains(
                    other = "nix",
                    ignoreCase = true
                ) -> OSType.Linux

            systemName
                .contains(
                    other = "mac",
                    ignoreCase = true
                ) -> OSType.Mac

            else -> error("unknown os")
        }
    }
