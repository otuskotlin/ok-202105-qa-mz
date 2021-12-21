plugins {
    kotlin("jvm")
}

dependencies {
    val kotlinVersion: String by project
    val logbackVersion: String by project
    val logbackEncoderVersion: String by project
    val logbackKafkaVersion: String by project
    val coroutinesVersion: String by project
    val janinoVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // allow to serialize logback messages to json
    implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")
    implementation("com.github.danielwegener:logback-kafka-appender:$logbackKafkaVersion")
    implementation("org.codehaus.janino:janino:$janinoVersion")
    api("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
