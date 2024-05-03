package com.aenadgrleey.kdb.plugins

import com.aenadgrleey.kdb.utils.detektPlugins
import com.aenadgrleey.kdb.utils.implementation
import com.aenadgrleey.kdb.utils.library
import com.aenadgrleey.kdb.utils.libs
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class KotlinConfig : Plugin<Project> {
    override fun apply(target: Project) {

        target.apply(plugin = "org.jetbrains.kotlin.jvm")
        target.apply(plugin = "io.gitlab.arturbosch.detekt")

        target.version = "1.0"
        target.group = "com.aenadgrleey"
        target.extensions
            .configure(KotlinJvmProjectExtension::class.java) {
                jvmToolchain(17)
            }
        target.extensions
            .configure(DetektExtension::class.java) {
                autoCorrect = true
                isIgnoreFailures = false
                buildUponDefaultConfig = true
                config.setFrom(target.rootDir.path + "/lint/lint.yaml")
            }


        val detekt = target.tasks.findByName("detekt")!!

        val check = target.tasks.findByName("check")!!
        check.finalizedBy(detekt)

        val compile = target.tasks.findByName("compileKotlin")!!
        compile.finalizedBy(detekt)

        val libs = target.libs
        target.dependencies {
            implementation(libs.library("kotlin-coroutines"))
            detektPlugins(libs.library("detekt-faireRules"))
            detektPlugins(libs.library("detekt-hbMartinRules"))
        }
    }
}

@Suppress("ConstPropertyName")
object KDB {
    const val debugger = ":debugger"
    const val api = ":debugger:api"
    const val impl = ":debugger:impl"
    const val sample = ":sample"
}

