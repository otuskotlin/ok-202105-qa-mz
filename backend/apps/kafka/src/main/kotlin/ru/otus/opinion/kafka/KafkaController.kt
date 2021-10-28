package ru.otus.opinion.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.WakeupException
import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.transport.mapping.setQuery
import ru.otus.opinion.transport.mapping.toResponse
import ru.otus.opinion.openapi.transport.models.Request
import ru.otus.opinion.openapi.transport.models.Response
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class KafkaController(private val config: KafkaConfig) {
    private val consumer = config.kafkaConsumer
    private val producer = config.kafkaProducer
    private val service = config.service
    private val om = ObjectMapper()
    private val process = AtomicBoolean(true)

    fun run() = runBlocking {
        try {
            consumer.subscribe(listOf(config.kafkaTopicIn))
            while (process.get()) {
                val ctx = RequestContext(
                    startTime = Instant.now(),
                )
                try {
                    val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(1))
                    records.forEach { record: ConsumerRecord<String, String> ->
                        val request =
                            withContext(Dispatchers.IO) { om.readValue(record.value(), Request::class.java) }
                        ctx.setQuery(request)
                        sendResponse(service.handle(ctx).toResponse())
                    }
                } catch (ex: Throwable) {
                    withContext(NonCancellable) {
                        sendResponse(ctx.addError(ServerError(ex)).toResponse())
                    }
                }
            }
        } catch (ex: WakeupException) {
            // ignore for shutdown
        } catch (ex: RuntimeException) {
            // exception handling
            withContext(NonCancellable) {
                throw ex
            }
        } finally {
            withContext(NonCancellable) {
                consumer.close()
            }
        }
    }

    private suspend fun sendResponse(response: Response) {
        val json = withContext(Dispatchers.IO) { om.writeValueAsString(response) }
        val resRecord = ProducerRecord(
            config.kafkaTopicOut,
            UUID.randomUUID().toString(),
            json
        )
        producer.send(resRecord)
    }

    fun stop() {
        process.set(false)
    }
}