package ru.otus.opinion.repo.inmemory

import ru.otus.opinion.repo.api.test.ListTestBase

class ListTest : ListTestBase() {
    override val repo = CacheBasedRepo()
}