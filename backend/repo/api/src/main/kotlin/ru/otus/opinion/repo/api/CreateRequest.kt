package ru.otus.opinion.repo.api

import ru.otus.opinion.models.Question

data class CreateRequest(
    val question: Question
)