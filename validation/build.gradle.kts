plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":common"))
    implementation(project(":common-cor"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
