package ru.otus.opinion.logics

import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.logics.chains.QuestionCreator
import ru.otus.opinion.logics.chains.QuestionsListReader

class Crud(val config: CrudConfig = CrudConfig()) {
    suspend fun create(ctx: RequestContext) {
        QuestionCreator.process(ctx)
    }
    suspend fun list(ctx: RequestContext) {
        QuestionsListReader.process(ctx)
    }
}