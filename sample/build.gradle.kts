import com.aenadgrleey.kdb.plugins.KDB

plugins {
    id("kotlin-config")
}


dependencies {
    implementation(project(KDB.debugger))
}

kotlin {
    jvmToolchain(17)
}