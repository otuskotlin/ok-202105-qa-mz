import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.opinion.kafka.KafkaConfig
import ru.otus.opinion.kafka.KafkaController
import ru.otus.opinion.openapi.transport.models.CreateQuestionResponse
import ru.otus.opinion.openapi.transport.models.Discriminable
import ru.otus.opinion.openapi.transport.models.Response
import ru.otus.opinion.openapi.transport.models.Result
import ru.otus.opinion.openapi.transport.models.stubs.Stubs
import ru.otus.opinion.services.QuestionService
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KafkaControllerTest {
    private val om = ObjectMapper()
    private fun Discriminable.toJson(): String = om.writeValueAsString(this)
    private inline fun <reified T: Discriminable> String.fromJson() = om.readValue<T>(this, T::class.java)

    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer(true, StringSerializer(), StringSerializer())
        val config = KafkaConfig(
            kafkaConsumer = consumer,
            kafkaProducer = producer,
            service = QuestionService.getTestService()
        )
        val app = KafkaController(config)
        val request = Stubs.createRequestA
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(config.kafkaTopicIn, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    config.kafkaTopicIn,
                    PARTITION,
                    0L,
                    "test-1",
                    request.toJson()
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(config.kafkaTopicIn, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val response = message.value().fromJson<Response>()
        assertTrue { response is CreateQuestionResponse }
        assertEquals(request.requestId, response.requestId)
        assertEquals(Result.SUCCESS, response.result)
        assertEquals(0, response.errors?.size)
        assertEquals(request.question, (response as CreateQuestionResponse).question)
    }

    companion object {
        const val PARTITION = 0
    }
}