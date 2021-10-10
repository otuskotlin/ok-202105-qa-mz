package ru.otus.opinion.logics.chains

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.cor.Worker
import ru.otus.opinion.backend.common.cor.dsl.chain
import ru.otus.opinion.logics.workers.checkRequestType
import ru.otus.opinion.logics.workers.finish
import ru.otus.opinion.logics.workers.init
import ru.otus.opinion.logics.workers.stubs.createQuestionsListStub

object QuestionsListReader: Worker<RequestContext> by chain<RequestContext> ({
    checkRequestType(RequestContext.RequestType.LIST)
    init()
    createQuestionsListStub()
    finish()
})