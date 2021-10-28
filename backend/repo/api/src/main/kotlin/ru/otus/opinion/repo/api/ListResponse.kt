package ru.otus.opinion.repo.api

import ru.otus.opinion.models.Question
import ru.otus.opinion.models.ServerError

class ListResponse(
    override val isSuccess: Boolean = true,
    override val errors: List<ServerError> = emptyList(),
    override val content: List<Question> = emptyList(),
) : RepoResponse<List<Question>>
