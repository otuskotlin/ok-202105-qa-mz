package ru.otus.opinion.repo.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec
import com.datastax.oss.driver.internal.core.type.codec.registry.DefaultCodecRegistry
import org.testcontainers.containers.CassandraContainer
import ru.otus.opinion.repo.api.Repo
import ru.otus.opinion.repo.api.test.CreateTestBase
import ru.otus.opinion.repo.api.test.ListTestBase
import ru.otus.opinion.repo.cassandra.dao.QuestionDaoFactoryBuilder
import ru.otus.opinion.repo.cassandra.dto.PermissionDto
import ru.otus.opinion.repo.cassandra.dto.QuestionDto
import ru.otus.opinion.repo.cassandra.dto.QuestionStateDto
import ru.otus.opinion.repo.cassandra.dto.QuestionVisibilityDto
import java.net.InetSocketAddress

class CreateTest : CreateTestBase() {
    override val repo: Repo = TestCompanion.createRepo()
}

class ListTest : ListTestBase() {
    override val repo: Repo = TestCompanion.createRepo()
}

class CassandraTestContainer: CassandraContainer<CassandraTestContainer>("cassandra:3.11")

object TestCompanion {
    private val container by lazy { CassandraTestContainer().apply { start() } }

    private val codecRegistry by lazy {
        DefaultCodecRegistry("default").apply {
            /** [EnumNameCodec] converts enum values to strings. */
            register(EnumNameCodec(PermissionDto::class.java))
            register(EnumNameCodec(QuestionStateDto::class.java))
            register(EnumNameCodec(QuestionVisibilityDto::class.java))
        }
    }

    private val session by lazy {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress(container.host, container.getMappedPort(CassandraContainer.CQL_PORT)))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(container.username, container.password)
            .withCodecRegistry(codecRegistry)
            .build()
    }

    private val daoFactory by lazy { QuestionDaoFactoryBuilder(session).build() }

    private fun createSchema(keyspace: String) {
        session.execute(
            SchemaBuilder.createKeyspace(keyspace).ifNotExists().withSimpleStrategy(1).build()
        )
        /** Create table. */
        session.execute(QuestionDto.table(keyspace, QuestionDto.TABLE_NAME))
        /** Create index on the 'title' column. */
        session.execute(QuestionDto.titleIndex(keyspace, QuestionDto.TABLE_NAME))
    }

    fun createRepo() : CassandraRepo {
        val keyspace = "data"
        createSchema(keyspace)
        val dao = daoFactory.buildQuestionDao(keyspace = keyspace, table = QuestionDto.TABLE_NAME)
        return CassandraRepo(dao = dao)
    }

}