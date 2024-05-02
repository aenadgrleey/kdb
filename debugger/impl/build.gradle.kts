import com.aenadgrleey.kdb.plugins.KDB

plugins {
    id("kotlin-config")
}

dependencies {
    implementation(project(KDB.api))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}