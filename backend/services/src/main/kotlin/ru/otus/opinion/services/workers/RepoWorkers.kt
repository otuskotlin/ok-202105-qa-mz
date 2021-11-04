package ru.otus.opinion.services.workers

import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.models.ProcessingMode
import ru.otus.opinion.models.State
import ru.otus.opinion.repo.api.CreateRequest
import ru.otus.opinion.repo.api.ListRequest
import ru.otus.opinion.repo.api.Repo
import ru.otus.opinion.services.ServiceContext

internal fun ChainBuilder<ServiceContext>.repoCreate() = worker {
    on { state == State.RUNNING}
    execute {
        val repo = selectRepo(this)
        val createRequest = CreateRequest(requestQuestion)
        val response = repo.create(createRequest)
        val question = response.content
        if (response.isSuccess && question != null) {
            responseQuestion = question
        } else {
            state = State.FAILED
            response.errors.forEach { addError(it) }
        }
    }
}

internal fun ChainBuilder<ServiceContext>.repoList() = worker {
    on { state == State.RUNNING}
    execute {
        val repo = selectRepo(this)
        val request = ListRequest(pagination)
        val response = repo.list(request)
        val questionsList = response.content
        if (response.isSuccess) {
            questions.addAll(questionsList)
        } else {
            state = State.FAILED
            response.errors.forEach { addError(it) }
        }
    }
}

private fun selectRepo(ctx : ServiceContext) : Repo = when(ctx.processingMode) {
    ProcessingMode.PROD -> ctx.serviceConfig.prodRepo
    ProcessingMode.STUB -> Repo.None
    ProcessingMode.TEST -> ctx.serviceConfig.testRepo
}