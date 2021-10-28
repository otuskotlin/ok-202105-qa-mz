package ru.otus.opinion.repo.api

import ru.otus.opinion.models.ServerError

interface RepoResponse<T> {
    val isSuccess: Boolean
    val errors: List<ServerError>
    val content: T
}