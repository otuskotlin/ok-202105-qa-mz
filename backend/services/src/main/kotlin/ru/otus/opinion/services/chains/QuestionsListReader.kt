package ru.otus.opinion.services.chains

import ru.otus.opinion.context.RequestType
import ru.otus.opinion.cor.Worker
import ru.otus.opinion.cor.dsl.chain
import ru.otus.opinion.services.ServiceContext
import ru.otus.opinion.services.validation.validateQuestionsListRequest
import ru.otus.opinion.services.workers.*

object QuestionsListReader: Worker<ServiceContext> by chain({
    checkRequestType(RequestType.LIST)
    init()
    createQuestionsListStub()
    validateQuestionsListRequest()
    repoList()
    finish()
})