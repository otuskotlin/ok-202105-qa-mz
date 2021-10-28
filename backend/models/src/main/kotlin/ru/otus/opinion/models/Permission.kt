package ru.otus.opinion.models

enum class Permission {
    READ, UPDATE, DELETE;

    companion object {
        val default = READ
    }
}