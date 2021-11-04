plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":backend:transport:openapi"))
    implementation(project(":backend:models"))

    testImplementation(kotlin("test"))
}
