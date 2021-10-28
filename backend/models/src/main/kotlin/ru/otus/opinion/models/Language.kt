package ru.otus.opinion.models

@JvmInline
value class Language(val id: String) {
    companion object {
        val UNDEFINED = Language("UNDEFINED")
    }
}