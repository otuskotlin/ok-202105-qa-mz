val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

fun ktor(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-$module:$version"

plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")

    /** Required to build fat jar. */
    id("com.github.johnrengelman.shadow") version "7.0.0"
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

/**
 * Build fat jar, and put it in build/libs folder.
 */
tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.example.ApplicationKt"))
        }
    }
}

dependencies {
    implementation(ktor("server-core"))
    implementation(ktor("server-netty"))
    implementation(ktor("jackson"))

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(project(":backend:models"))
    implementation(project(":backend:services"))
    implementation(project(":backend:transport:openapi"))
    implementation(project(":backend:transport:mapping"))

    testImplementation(ktor("server-test-host"))
    testImplementation(kotlin("test-junit"))
//    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
}