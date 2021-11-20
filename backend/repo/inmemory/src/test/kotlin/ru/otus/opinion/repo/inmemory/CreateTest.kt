package ru.otus.opinion.repo.inmemory

import ru.otus.opinion.repo.api.test.CreateTestBase

class CreateTest : CreateTestBase() {
    override val repo = CacheBasedRepo()
}