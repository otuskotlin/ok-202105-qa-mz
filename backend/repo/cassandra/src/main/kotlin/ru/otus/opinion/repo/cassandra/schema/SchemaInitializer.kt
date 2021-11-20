package ru.otus.opinion.repo.cassandra.schema

import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import ru.otus.opinion.repo.cassandra.CassandraRepo
import ru.otus.opinion.repo.cassandra.dao.QuestionDaoFactoryBuilder

class SchemaInitializer {

    /**
     * Check db schema and create tables if required.
     * Build repository implementation based on [CASSANDRA_PROPERTIES_FILE].
     */
    fun init(): CassandraRepo = init(cassandraProperties())

    /**
     * Check db schema and create tables if required.
     * Build repository implementation based on provided properties.
     */
    fun init(properties: CassandraProperties): CassandraRepo {
        val connector = CassandraConnector(properties)
        val session = connector.session
        val keyspace = properties.keyspace

        /** Create keyspace if required. */
        session.execute(
            SchemaBuilder.createKeyspace(keyspace).ifNotExists().withSimpleStrategy(1).build()
        )
        /** Create table if required. */
        session.execute(table(keyspace))

        /** Create index on the 'title' column. */
        session.execute(titleIndex(keyspace))

        val daoFactory = QuestionDaoFactoryBuilder(session).build()
        val dao = daoFactory.buildQuestionDao(keyspace, TABLE_NAME)
        return CassandraRepo(dao = dao)
    }

    /**
     * Build CQL statement which creates table.
     */
    private fun table(keyspace: String) =
        SchemaBuilder
            .createTable(keyspace, TABLE_NAME)
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
     * Build CQL statement which creates index on the 'title' column.
     */
    private fun titleIndex(keyspace: String, locale: String = "en") =
        SchemaBuilder
            .createIndex()
            .ifNotExists()
            .usingSASI()
            .onTable(keyspace, TABLE_NAME)
            .andColumn(TITLE_COLUMN)
            .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to locale))
            .build()

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
    }
}