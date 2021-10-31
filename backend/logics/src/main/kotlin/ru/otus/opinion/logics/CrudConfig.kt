package ru.otus.opinion.logics

import ru.otus.opinion.repo.api.Repo

class CrudConfig (
    val prodRepo: Repo = Repo.None,
    val testRepo: Repo = Repo.None
)