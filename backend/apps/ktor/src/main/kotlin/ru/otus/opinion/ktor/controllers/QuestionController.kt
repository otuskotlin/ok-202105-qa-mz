package ru.otus.opinion.ktor.controllers

import io.ktor.application.*
import ru.otus.opinion.openapi.transport.models.Question

interface QuestionController {
    suspend fun defaultQuestions(): List<Question>
    suspend fun create(call: ApplicationCall)
    suspend fun list(call: ApplicationCall)
}