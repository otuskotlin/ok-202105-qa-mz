package ru.otus.opinion.repo.api

import ru.otus.opinion.models.Question

data class SaveRequest(
    val question: Question
)