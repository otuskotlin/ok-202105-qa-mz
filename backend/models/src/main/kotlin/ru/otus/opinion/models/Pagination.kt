package ru.otus.opinion.models

data class Pagination (
    val count: Int = 0,
    val id: String = "",
    val relation: Relation = Relation.AFTER
)

enum class Relation {
    BEFORE, AFTER
}
