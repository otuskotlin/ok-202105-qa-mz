package ru.otus.opinion.transport.logging

import ru.otus.opinion.openapi.transport.models.*

data class CommonLogModel(
    val requestType: String? = null,
    val processingMode: String? = null,
    val requestId: String?,
    val requestQuestion: Question? = null,
    val responseQuestion: Question? = null,
    val questions: List<Question>? = null,
    val errors: List<ServerError>? = null
)