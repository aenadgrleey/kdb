plugins {
    kotlin("jvm") version "2.0.0-RC2"
}

group = "com.aenadgrleey"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}