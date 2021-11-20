package ru.otus.opinion.repo.cassandra

import org.testcontainers.containers.CassandraContainer
import ru.otus.opinion.repo.api.Repo
import ru.otus.opinion.repo.api.test.CreateTestBase
import ru.otus.opinion.repo.api.test.ListTestBase
import ru.otus.opinion.repo.cassandra.schema.CassandraProperties
import ru.otus.opinion.repo.cassandra.schema.SchemaInitializer

class CreateTest : CreateTestBase() {
    override val repo: Repo = TestCompanion.createRepo()
}

class ListTest : ListTestBase() {
    override val repo: Repo = TestCompanion.createRepo()
}

class CassandraTestContainer: CassandraContainer<CassandraTestContainer>("cassandra:3.11")

object TestCompanion {
    private val container by lazy { CassandraTestContainer().apply { start() } }

    /**
     * Create test instance of repository.
     */
    fun createRepo() : CassandraRepo {
        val properties = CassandraProperties(
            keyspace = "data",
            port = container.getMappedPort(CassandraContainer.CQL_PORT),
            host = container.host,
            username = container.username,
            password = container.password,
            datacenter = "datacenter1"
        )
        return SchemaInitializer().init(properties)
    }
}