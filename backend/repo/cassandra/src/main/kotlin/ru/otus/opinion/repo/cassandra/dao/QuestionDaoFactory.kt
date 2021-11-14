package ru.otus.opinion.repo.cassandra.dao

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface QuestionDaoFactory {
    @DaoFactory
    fun buildQuestionDao(@DaoKeyspace keyspace: String, @DaoTable table: String): QuestionDao
}