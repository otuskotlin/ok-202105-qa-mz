plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))
    implementation(project(":model-stubs"))
}
