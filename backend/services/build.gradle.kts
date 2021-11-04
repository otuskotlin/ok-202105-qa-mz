plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(kotlin("stdlib"))
    implementation(project(":backend:dsl:cor"))
    implementation(project(":backend:models"))
    implementation(project(":backend:repo:api"))
    implementation(project(":backend:repo:inmemory"))
    implementation(project(":backend:validation"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
