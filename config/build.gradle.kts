plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}


dependencies {
    implementation(gradleApi())
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins.register("kotlin-config") {
        id = "kotlin-config"
        implementationClass = "com.aenadgrleey.kdb.plugins.KotlinConfig"
    }
}