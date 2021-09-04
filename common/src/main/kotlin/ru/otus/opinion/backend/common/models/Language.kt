package ru.otus.opinion.backend.common.models

@JvmInline
value class Language(val id: String) {
    companion object {
        val UNDEFINED = Language("UNDEFINED")
    }
}