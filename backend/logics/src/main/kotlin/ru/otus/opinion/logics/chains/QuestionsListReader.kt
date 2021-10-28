package ru.otus.opinion.logics.chains

import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.cor.Worker
import ru.otus.opinion.cor.dsl.chain
import ru.otus.opinion.logics.validation.validateQuestionsListRequest
import ru.otus.opinion.logics.workers.checkRequestType
import ru.otus.opinion.logics.workers.finish
import ru.otus.opinion.logics.workers.init
import ru.otus.opinion.logics.workers.createQuestionsListStub

object QuestionsListReader: Worker<RequestContext> by chain({
    checkRequestType(RequestContext.RequestType.LIST)
    init()
    createQuestionsListStub()
    validateQuestionsListRequest()
    finish()
})