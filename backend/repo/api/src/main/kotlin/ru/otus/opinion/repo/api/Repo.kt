package ru.otus.opinion.repo.api

interface Repo {
    suspend fun create(createRequest: CreateRequest): CreateResponse
    suspend fun list(listRequest: ListRequest): ListResponse

    object None: Repo {
        override suspend fun create(createRequest: CreateRequest): CreateResponse {
            TODO("Not yet implemented")
        }

        override suspend fun list(listRequest: ListRequest): ListResponse {
            TODO("Not yet implemented")
        }
    }
}