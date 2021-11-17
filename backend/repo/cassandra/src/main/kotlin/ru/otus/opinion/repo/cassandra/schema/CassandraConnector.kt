package ru.otus.opinion.repo.cassandra.schema

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec
import com.datastax.oss.driver.internal.core.type.codec.registry.DefaultCodecRegistry
import ru.otus.opinion.repo.cassandra.dto.PermissionDto
import ru.otus.opinion.repo.cassandra.dto.QuestionStateDto
import ru.otus.opinion.repo.cassandra.dto.QuestionVisibilityDto
import java.net.InetSocketAddress

/**
 * Builds Cassandra session based on provided properties.
 */
class CassandraConnector(properties: CassandraProperties) {

    private val codecRegistry = DefaultCodecRegistry("default").apply {
        /** [EnumNameCodec] converts enum values to strings. */
        register(EnumNameCodec(PermissionDto::class.java))
        register(EnumNameCodec(QuestionStateDto::class.java))
        register(EnumNameCodec(QuestionVisibilityDto::class.java))
    }

    internal val session: CqlSession = run {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress(properties.host, properties.port))
            .withLocalDatacenter(properties.datacenter)
//            .withAuthCredentials(properties.username, properties.password)
            .withCodecRegistry(codecRegistry)
            .build()
    }

    fun close() {
        session.close()
    }
}