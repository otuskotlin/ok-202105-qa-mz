package ru.otus.opinion.models

enum class QuestionVisibility {
    OWNER_ONLY, REGISTERED_ONLY, PUBLIC;

    companion object {
        val default = OWNER_ONLY
    }
}