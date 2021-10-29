plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":backend:models"))

    /**
     * TODO: change to testImplementation dependency when classes from `test`
     * subpackage will be moved to test source.
     *
     * Required because we cannot move base test classes to the test source
     * due to bug in gradle test-fixtures plugin:
     * https://github.com/gradle/gradle/issues/11501
     */
    val coroutinesVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation(kotlin("test-junit"))
}
