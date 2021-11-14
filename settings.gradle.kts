/*
 * The settings file is used to specify which projects to include in your build.
 *
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user manual at https://docs.gradle.org/7.1/userguide/multi_project_builds.html
 */

rootProject.name = "opinion"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openApiVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        id("org.openapi.generator") version openApiVersion
        id("com.bmuschko.docker-java-application") version bmuschkoVersion
    }
}


include(":backend:apps:kafka")
include(":backend:apps:ktor")
include(":backend:models")
include(":backend:context")
include(":backend:dsl:cor")
include(":backend:services")
include(":backend:transport:mapping")
include(":backend:transport:openapi")
include(":backend:validation")
include(":backend:repo:api")
include(":backend:repo:cassandra")
include(":backend:repo:inmemory")

include(":multiplatform:common-mp")
