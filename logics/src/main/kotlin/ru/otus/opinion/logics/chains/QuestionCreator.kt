package ru.otus.opinion.logics.chains

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.cor.Worker
import ru.otus.opinion.backend.common.cor.dsl.chain
import ru.otus.opinion.logics.workers.checkRequestType
import ru.otus.opinion.logics.workers.finish
import ru.otus.opinion.logics.workers.init
import ru.otus.opinion.logics.workers.stubs.createQuestionStub

object QuestionCreator: Worker<RequestContext> by chain<RequestContext> ({
    checkRequestType(RequestContext.RequestType.CREATE)
    init()
    createQuestionStub()
    finish()
})