package ru.otus.opinion.services

import ru.otus.opinion.repo.api.Repo
import ru.otus.opinion.repo.inmemory.CacheBasedRepo

class ServiceConfig (
    val prodRepo: Repo = Repo.None,
    val testRepo: Repo = Repo.None
) {
    companion object {
        fun getServiceConfig() = ServiceConfig(
            prodRepo = CacheBasedRepo(),
            testRepo = CacheBasedRepo()
        )
    }
}