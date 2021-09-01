import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import ru.otus.opinion.openapi.models.*
import kotlin.test.assertEquals

class SerializationTest {
    private val mapper = ObjectMapper()

    @Test
    fun serializationTest() {
        val request = QuestionsRequest(
            requestId = "1",
            processingMode = ProcessingMode.PROD,
            pagination = Pagination(objectsCount = 10, objectId = "1", relation = Pagination.Relation.AFTER)
        )
        val jsonString = mapper.writeValueAsString(request)
        println(jsonString)
        val deserialized = mapper.readValue(jsonString, Discriminable::class.java) as QuestionsRequest
        assertEquals(request.requestId, deserialized.requestId)
        assertEquals(request.processingMode, deserialized.processingMode)
        val pagination = request.pagination
        val deserializedPagination = deserialized.pagination
        assertEquals(pagination?.objectsCount, deserializedPagination?.objectsCount)
        assertEquals(pagination?.objectId, deserializedPagination?.objectId)
        assertEquals(pagination?.relation, deserializedPagination?.relation)
    }


}