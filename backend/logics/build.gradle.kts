plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":backend:models"))
    implementation(project(":backend:context"))
    implementation(project(":backend:dsl:cor"))
    implementation(project(":backend:validation"))
    implementation(project(":backend:repo:api"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}
