package ru.otus.opinion.logics.chains

import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.cor.Worker
import ru.otus.opinion.cor.dsl.chain
import ru.otus.opinion.logics.validation.validateCreateRequest
import ru.otus.opinion.logics.workers.*

object QuestionCreator: Worker<RequestContext> by chain({
    checkRequestType(RequestContext.RequestType.CREATE)
    init()
    createQuestionStub()
    validateCreateRequest()
//    worker {
//        execute {
//            // appropriate validation logic
//        }
//    }
    finish()
})