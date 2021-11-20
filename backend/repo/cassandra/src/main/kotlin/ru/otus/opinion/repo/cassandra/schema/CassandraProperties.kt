package ru.otus.opinion.repo.cassandra.schema

import java.util.*

const val CASSANDRA_PROPERTIES_FILE: String = "cassandra.properties"

/**
 * Read Cassandra connection properties from [CASSANDRA_PROPERTIES_FILE].
 */
fun cassandraProperties(): CassandraProperties {
    val inSteam = Thread.currentThread()
        .contextClassLoader
        .getResourceAsStream(CASSANDRA_PROPERTIES_FILE)
        ?: throw IllegalArgumentException("Failed to read Cassandra properties from file: $CASSANDRA_PROPERTIES_FILE")
    val props = Properties()
    props.load(inSteam)
    val keyspace = props.getProperty("keyspace", "opinion")
    val port = props.getProperty("cassandraPort", "9042").toInt()
    val host = props.getProperty("cassandraHost", "localhost")
    val datacenter = props.getProperty("datacenter", "datacenter1")
    return CassandraProperties(
        keyspace = keyspace,
        port = port,
        host = host,
        username = "",
        password = "",
        datacenter = datacenter,
    )
}

data class CassandraProperties(
    val keyspace: String,
    val port: Int,
    val host:String,
    val username: String,
    val password: String,
    val datacenter: String
)