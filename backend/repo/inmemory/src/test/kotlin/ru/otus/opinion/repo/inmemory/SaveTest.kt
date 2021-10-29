package ru.otus.opinion.repo.inmemory

import ru.otus.opinion.repo.api.test.SaveTestBase

class SaveTest : SaveTestBase() {
    override val repo = CacheBasedRepo()
}