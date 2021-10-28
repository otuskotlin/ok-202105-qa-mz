package ru.otus.opinion.models

@JvmInline
value class RequestId(val id: String) {
    companion object {
        val EMPTY = RequestId("")
    }
}