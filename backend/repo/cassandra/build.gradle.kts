plugins {
    kotlin("jvm")
    // annotations processing - generate source code from annotations
    kotlin("kapt")
}

dependencies {
    val coroutinesVersion: String by project
    val cassandraDriverVersion: String by project
    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))
    implementation(project(":backend:repo:api"))
    implementation(project(":backend:models"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    // library used to work with CompletableStage
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")

    // core driver
    implementation("com.datastax.oss:java-driver-core:$cassandraDriverVersion")
    //implementation("com.datastax.cassandra:cassandra-driver-core:$cassandraDriverVersion")
    // CQL query builder
    implementation("com.datastax.oss:java-driver-query-builder:$cassandraDriverVersion")

    // allows map objects (data classes) to cassandra tables
    kapt("com.datastax.oss:java-driver-mapper-processor:$cassandraDriverVersion")
    implementation("com.datastax.oss:java-driver-mapper-runtime:$cassandraDriverVersion")

    testImplementation("org.testcontainers:cassandra:$testContainersVersion")
}
