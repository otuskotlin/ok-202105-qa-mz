package ru.otus.opinion.ktor.configs

import io.ktor.application.*
import ru.otus.opinion.services.QuestionService

data class AppConfig(
    val service: QuestionService = QuestionService.getService()
) {
    constructor(environment: ApplicationEnvironment): this()

    companion object {
        val TEST_CONFIG: AppConfig = AppConfig(QuestionService.getTestService())
    }
}
