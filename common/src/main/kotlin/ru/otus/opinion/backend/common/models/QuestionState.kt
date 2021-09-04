package ru.otus.opinion.backend.common.models

enum class QuestionState {
    PROPOSED, MODERATED, ACCEPTED, OPENED, CLOSED;

    companion object {
        val default = PROPOSED
    }
}