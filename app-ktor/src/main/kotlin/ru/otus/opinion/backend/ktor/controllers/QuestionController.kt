package ru.otus.opinion.backend.ktor.controllers

import io.ktor.application.*

interface QuestionController {
    suspend fun create(call: ApplicationCall)
    suspend fun list(call: ApplicationCall)
}