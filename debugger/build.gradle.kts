import com.aenadgrleey.kdb.plugins.KDB

plugins {
    id("kotlin-config")
}

dependencies {
    api(project(KDB.api))
    implementation(project(KDB.impl))
}
