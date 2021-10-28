package ru.otus.opinion.repo.api

interface Repo {
    suspend fun save(saveRequest: SaveRequest): SaveResponse
    suspend fun list(listRequest: ListRequest): ListResponse

    object None: Repo {
        override suspend fun save(saveRequest: SaveRequest): SaveResponse {
            TODO("Not yet implemented")
        }

        override suspend fun list(listRequest: ListRequest): ListResponse {
            TODO("Not yet implemented")
        }
    }
}