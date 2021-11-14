package ru.otus.opinion.repo.cassandra.dto

import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import ru.otus.opinion.models.*
import java.time.Instant

@Entity
data class QuestionDto (
    @PartitionKey
    @field:CqlName(ID_COLUMN)
    val id: String? = null,
    @field:CqlName(TITLE_COLUMN)
    var title: String? = null,
    @field:CqlName(CONTENT_COLUMN)
    var content: String? = null,
    @field:CqlName(AUTHOR_COLUMN)
    var author: String? = null,
    @field:CqlName(CREATION_TIME_COLUMN)
    var creationTime: String? = null,
    @field:CqlName(LANGUAGE_COLUMN)
    var language: String? = null,
    @field:CqlName(QUESTION_TAGS_COLUMN)
    var tags: List<String>? = null,
    @field:CqlName(LIKES_COUNT_COLUMN)
    var likesCount: Int? = null,
    @field:CqlName(ANSWERS_COUNT_COLUMN)
    var answersCount: Int? = null,
    @field:CqlName(PERMISSIONS_COLUMN)
    var permissions: Set<PermissionDto>? = null,
    @field:CqlName(QUESTION_STATE_COLUMN)
    var state: QuestionStateDto? = null,
    @field:CqlName(VISIBILITY_COLUMN)
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
        permissions = permissions?.map { ModelDtoMapper.toModel(it) }?.toSet() ?: emptySet(),
        state = state?.let { ModelDtoMapper.toModel(it)} ?: QuestionState.default,
        visibility = visibility?.let { ModelDtoMapper.toModel(it)} ?: QuestionVisibility.default
    )

    companion object {
        const val TABLE_NAME = "questions"

        const val ID_COLUMN = "id"
        const val TITLE_COLUMN = "title"
        const val CONTENT_COLUMN = "content"
        const val AUTHOR_COLUMN = "author"
        const val CREATION_TIME_COLUMN = "creation_time"
        const val LANGUAGE_COLUMN = "language"
        const val QUESTION_TAGS_COLUMN = "question_tags"
        const val LIKES_COUNT_COLUMN = "likes_count"
        const val ANSWERS_COUNT_COLUMN = "answers_count"
        const val PERMISSIONS_COLUMN = "permissions"
        const val QUESTION_STATE_COLUMN = "question_state"
        const val VISIBILITY_COLUMN = "visibility"

        /**
         * Build simple CQL statement used to create table.
         */
        fun table(keyspace: String, tableName: String) =
            SchemaBuilder
                .createTable(keyspace, tableName)
                .ifNotExists()
                .withPartitionKey(ID_COLUMN, DataTypes.TEXT)
                .withColumn(TITLE_COLUMN, DataTypes.TEXT)
                .withColumn(CONTENT_COLUMN, DataTypes.TEXT)
                .withColumn(AUTHOR_COLUMN, DataTypes.TEXT)
                .withColumn(CREATION_TIME_COLUMN, DataTypes.TEXT) // TODO: change to TIMESTAMP data type
                .withColumn(LANGUAGE_COLUMN, DataTypes.TEXT)
                .withColumn(QUESTION_TAGS_COLUMN, DataTypes.listOf(DataTypes.TEXT))
                .withColumn(LIKES_COUNT_COLUMN, DataTypes.INT)
                .withColumn(ANSWERS_COUNT_COLUMN, DataTypes.INT)
                .withColumn(PERMISSIONS_COLUMN, DataTypes.setOf(DataTypes.TEXT))
                .withColumn(QUESTION_STATE_COLUMN, DataTypes.TEXT)
                .withColumn(VISIBILITY_COLUMN, DataTypes.TEXT)
                .build()


        /**
         * Build simple CQL statement used to create index on the 'title' column.
         */
        fun titleIndex(keyspace: String, tableName: String, locale: String = "en") =
            SchemaBuilder
                .createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(keyspace, tableName)
                .andColumn(TITLE_COLUMN)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to locale))
                .build()

    }
}