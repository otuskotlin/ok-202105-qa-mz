tasks.register<Copy>("copyVueToKtor") {
    from("$projectDir/dist/")
    into("../backend/apps/ktor/src/main/resources/web/dist/")
}
