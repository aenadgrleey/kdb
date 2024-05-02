package com.aenadgrleey.kdb.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import kotlin.jvm.java

val Project.libs: VersionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).find("libs").get()

fun VersionCatalog.library(name: String) = findLibrary(name).get()

