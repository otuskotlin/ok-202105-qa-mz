import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import ru.otus.opinion.openapi.models.Discriminable
import ru.otus.opinion.openapi.models.Pagination
import ru.otus.opinion.openapi.models.QuestionsListRequest
import ru.otus.opinion.openapi.models.RequestProcessingMode
import kotlin.test.assertEquals

class SerializationTest {
    private val mapper = ObjectMapper()

    @Test
    fun serializationTest() {
        val request = QuestionsListRequest(
            requestId = "1",
            processingMode = RequestProcessingMode(mode = RequestProcessingMode.Mode.PROD, stub = null),
            pagination = Pagination(size = 10, lastId = "0")
        )
        val jsonString = mapper.writeValueAsString(request)
        println(jsonString)
        val deserialized = mapper.readValue(jsonString, Discriminable::class.java) as QuestionsListRequest
        assertEquals(request.requestId, deserialized.requestId)
    }

}