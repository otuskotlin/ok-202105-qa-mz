plugins {
    kotlin("jvm")
}
dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":backend:models"))
    implementation(project(":backend:transport:openapi"))
    implementation(project(":backend:transport:mapping"))
}
