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
