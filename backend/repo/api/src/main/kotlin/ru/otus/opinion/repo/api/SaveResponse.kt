package ru.otus.opinion.repo.api

import ru.otus.opinion.models.Question
import ru.otus.opinion.models.ServerError

data class SaveResponse(
    override val isSuccess: Boolean = true,
    override val errors: List<ServerError> = emptyList(),
    override val content: Question? = null
) : RepoResponse<Question?>