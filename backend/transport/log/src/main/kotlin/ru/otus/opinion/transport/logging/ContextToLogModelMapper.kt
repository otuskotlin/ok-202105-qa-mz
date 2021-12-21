package ru.otus.opinion.transport.logging

import ru.otus.opinion.context.IRequestContext
import ru.otus.opinion.transport.mapping.toTransport

fun toLogModel(ctx: IRequestContext) = CommonLogModel(
    requestType = ctx.requestType.name,
    processingMode = ctx.processingMode.name,
    requestId = ctx.requestId.id,
    requestQuestion = ctx.requestQuestion.toTransport(),
    responseQuestion = ctx.responseQuestion.toTransport(),
    questions = ctx.questions.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    errors = ctx.errors.takeIf { it.isNotEmpty() }?.map { it.toTransport()}
)