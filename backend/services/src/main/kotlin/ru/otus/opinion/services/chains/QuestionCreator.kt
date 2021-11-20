package ru.otus.opinion.services.chains

import ru.otus.opinion.context.RequestType
import ru.otus.opinion.cor.Worker
import ru.otus.opinion.cor.dsl.chain
import ru.otus.opinion.services.*
import ru.otus.opinion.services.validation.validateCreateRequest
import ru.otus.opinion.services.workers.*
import ru.otus.opinion.services.workers.checkRequestType
import ru.otus.opinion.services.workers.createQuestionStub
import ru.otus.opinion.services.workers.init
import ru.otus.opinion.services.workers.repoCreate

object QuestionCreator: Worker<ServiceContext> by chain({
    checkRequestType(RequestType.CREATE)
    init()
    createQuestionStub()
    validateCreateRequest()
    repoCreate()
    finish()
})