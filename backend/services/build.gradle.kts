plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":backend:models"))
    implementation(project(":backend:context"))
    implementation(project(":backend:logics"))
}
