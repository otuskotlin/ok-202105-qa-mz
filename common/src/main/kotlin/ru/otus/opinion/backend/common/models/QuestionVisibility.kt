package ru.otus.opinion.backend.common.models

enum class QuestionVisibility {
    OWNER_ONLY, REGISTERED_ONLY, PUBLIC;

    companion object {
        val default = OWNER_ONLY
    }
}