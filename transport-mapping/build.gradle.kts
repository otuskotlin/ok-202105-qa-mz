plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":transport-openapi"))
    implementation(project(":transport-openapi-stubs"))
    implementation(project(":model-stubs"))
    implementation(project(":common"))

    testImplementation(kotlin("test"))
}
