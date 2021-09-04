plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":transport-openapi"))
    implementation(project(":common"))

    testImplementation(kotlin("test"))
}
