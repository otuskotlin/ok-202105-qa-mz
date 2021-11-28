plugins {
    id("org.siouan.frontend-jdk11")
}

apply(plugin = "org.siouan.frontend-jdk11")

/* View all siouan plugin settings at https://siouan.github.io/frontend-gradle-plugin/configuration. */
frontend {
    nodeDistributionProvided.set(true)

    // we use yarn for building
    yarnEnabled.set(true)
    yarnDistributionProvided.set(true)

    installScript.set("install")
    assembleScript.set("build")
}

tasks.register<Copy>("copyVueToKtor") {
    from("$projectDir/dist/")
    into("../backend/apps/ktor/src/main/resources/web/dist/")
}

/* Install multiplatform's 'transport-models' package with yarn. */
tasks.register<Exec>("addTransportModels") {
    outputs.upToDateWhen { false }
    workingDir = projectDir
    commandLine("yarn", "remove", "transport-models")
    commandLine("yarn", "add", "file:../multiplatform/build/libs/transportModels/")
}

tasks {
    build {
        dependsOn(":multiplatform:build")
    }
}