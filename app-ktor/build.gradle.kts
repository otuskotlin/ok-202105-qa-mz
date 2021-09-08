val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

fun ktor(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-$module:$version"

plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(ktor("server-core"))
    implementation(ktor("server-netty"))
    implementation(ktor("jackson"))

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // transport models
    implementation(project(":common"))
    implementation(project(":transport-openapi"))
    implementation(project(":transport-mapping"))

    // services
    implementation(project(":app-services"))

    // stubs
    implementation(project(":model-stubs"))

    testImplementation(ktor("server-test-host"))
    testImplementation(kotlin("test-junit"))
//    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
}