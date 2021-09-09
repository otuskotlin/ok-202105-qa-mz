val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

fun ktor(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-$module:$version"

plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
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