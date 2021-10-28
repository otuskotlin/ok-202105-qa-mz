package ru.otus.opinion.models

@JvmInline
value class UserId(val id: String) {
    companion object {
        val EMPTY = UserId("")
    }
}