package ru.otus.opinion.repo.cassandra

import ru.otus.opinion.repo.cassandra.schema.SchemaInitializer

fun main() {
    val builder = SchemaInitializer()
    builder.init()
}
