package ru.otus.opinion.backend.common.models

@JvmInline
value class UserId(val id: String) {
    companion object {
        val EMPTY = UserId("")
    }
}