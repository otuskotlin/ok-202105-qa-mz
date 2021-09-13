package ru.otus.opinion.backend.common.models

enum class Permission {
    READ, UPDATE, DELETE;

    companion object {
        val default = READ
    }
}