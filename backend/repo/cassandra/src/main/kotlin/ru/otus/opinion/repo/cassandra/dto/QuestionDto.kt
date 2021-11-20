package ru.otus.opinion.repo.cassandra.dto

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.opinion.models.*
import ru.otus.opinion.repo.cassandra.schema.SchemaInitializer
import java.time.Instant

@Entity
data class QuestionDto (
    @PartitionKey
    @field:CqlName(SchemaInitializer.ID_COLUMN)
    val id: String? = null,
    @field:CqlName(SchemaInitializer.TITLE_COLUMN)
    var title: String? = null,
    @field:CqlName(SchemaInitializer.CONTENT_COLUMN)
    var content: String? = null,
    @field:CqlName(SchemaInitializer.AUTHOR_COLUMN)
    var author: String? = null,
    @field:CqlName(SchemaInitializer.CREATION_TIME_COLUMN)
    var creationTime: String? = null,
    @field:CqlName(SchemaInitializer.LANGUAGE_COLUMN)
    var language: String? = null,
    @field:CqlName(SchemaInitializer.QUESTION_TAGS_COLUMN)
    var tags: List<String>? = null,
    @field:CqlName(SchemaInitializer.LIKES_COUNT_COLUMN)
    var likesCount: Int? = null,
    @field:CqlName(SchemaInitializer.ANSWERS_COUNT_COLUMN)
    var answersCount: Int? = null,
    @field:CqlName(SchemaInitializer.PERMISSIONS_COLUMN)
    var permissions: Set<PermissionDto>? = null,
    @field:CqlName(SchemaInitializer.QUESTION_STATE_COLUMN)
    var state: QuestionStateDto? = null,
    @field:CqlName(SchemaInitializer.VISIBILITY_COLUMN)
    var visibility: QuestionVisibilityDto? = null
) {
    constructor(question: Question) : this(
        id = question.questionId.takeIf { it != QuestionId.EMPTY }?.id,
        title = question.title.takeIf { it.isNotBlank() },
        content = question.content.takeIf { it.isNotBlank() },
        author = question.author.takeIf { it != UserId.EMPTY }?.id,
        creationTime = question.creationTime.toString(),
        language = question.language.id,
        tags = question.tags.map { tag -> tag.id },
        likesCount = question.likesCount,
        answersCount = question.answersCount,
        // TODO: do not store permissions, they should be calculated as part of the business logic
        permissions = question.permissions.map { ModelDtoMapper.toDto(it) }.toSet(),
        state = ModelDtoMapper.toDto(question.state),
        visibility = ModelDtoMapper.toDto(question.visibility)
    )

    fun toModel() : Question = Question(
        questionId = id?.let { QuestionId(it) } ?: QuestionId.EMPTY,
        title = title ?: "",
        content = content ?: "",
        author = author?.let { UserId(it) } ?: UserId.EMPTY,
        creationTime = creationTime?.let { Instant.parse(it) } ?: Instant.now(),
        language = language?.let { Language(it) } ?: Language.UNDEFINED,
        tags = tags?.map(::QuestionTag) ?: emptyList(),
        likesCount = likesCount ?: 0,
        answersCount = answersCount ?: 0,
        // TODO: do not store permissions, they should be calculated as part of the business logic
        permissions = permissions?.map { ModelDtoMapper.toModel(it) }?.toSet() ?: emptySet(),
        state = state?.let { ModelDtoMapper.toModel(it)} ?: QuestionState.default,
        visibility = visibility?.let { ModelDtoMapper.toModel(it)} ?: QuestionVisibility.default
    )
}