package ru.otus.opinion.repo.api

import ru.otus.opinion.models.ErrorLevel
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.Question
import ru.otus.opinion.models.ServerError

data class SaveResponse(
    override val isSuccess: Boolean = true,
    override val errors: List<ServerError> = emptyList(),
    override val content: Question? = null
) : RepoResponse<Question?> {
    constructor(error: String) : this(
        isSuccess = false,
        errors = listOf(ServerError(message = error, level = ErrorLevel.ERROR, errorType = ErrorType.DB_ERROR)),
        content = null)
}