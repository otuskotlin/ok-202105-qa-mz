package ru.otus.opinion.backend.common.models

@JvmInline
value class RequestId(val id: String) {
    companion object {
        val EMPTY = RequestId("")
    }
}