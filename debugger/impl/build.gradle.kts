import com.aenadgrleey.kdb.plugins.KDB

plugins {
    id("kotlin-config")
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(project(KDB.api))

    implementation(libs.kotlin.inject.runtime)
    ksp(libs.kotlin.inject.compiler)

    implementation(libs.napier)
    testImplementation(kotlin("test"))
}

ksp {
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
    arg("me.tatarka.inject.dumpGraph", "true")
}

tasks.test {
    useJUnitPlatform()
}