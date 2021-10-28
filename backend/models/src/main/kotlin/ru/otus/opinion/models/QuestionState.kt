package ru.otus.opinion.models

enum class QuestionState {
    PROPOSED, MODERATED, ACCEPTED, OPENED, CLOSED;

    companion object {
        val default = PROPOSED
    }
}