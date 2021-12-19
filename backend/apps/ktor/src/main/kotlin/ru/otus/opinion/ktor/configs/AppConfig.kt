package ru.otus.opinion.ktor.configs

import io.ktor.application.*
import ru.otus.opinion.services.QuestionService

data class AppConfig(
    val service: QuestionService = QuestionService.getService()
) {
    companion object {
        val TEST_CONFIG: AppConfig = AppConfig(QuestionService.getTestService())

        fun buildAppConfig(environment: ApplicationEnvironment): AppConfig {
            val mode = environment.config.propertyOrNull("ktor.environment")?.getString() ?: "PROD"
            return if (mode == "TEST") {
                TEST_CONFIG
            } else {
                AppConfig(QuestionService.getService())
            }
        }
    }
}
