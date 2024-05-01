plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "kdb"
include("sample")
include("debugger")
include("debugger:api")
include("debugger:impl")
