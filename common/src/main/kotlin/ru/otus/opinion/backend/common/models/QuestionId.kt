package ru.otus.opinion.backend.common.models

@JvmInline
value class QuestionId(val id: String) {
    companion object {
        val EMPTY = QuestionId("")
    }
}