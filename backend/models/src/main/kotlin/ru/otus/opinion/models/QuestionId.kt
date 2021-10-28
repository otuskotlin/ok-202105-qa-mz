package ru.otus.opinion.models

@JvmInline
value class QuestionId(val id: String) {
    companion object {
        val EMPTY = QuestionId("")
    }
}