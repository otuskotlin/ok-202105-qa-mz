import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.Test

/* Annotation to enforce to run 'tearUp' as not static method. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JUnitTest {
    @BeforeAll
    fun tearUp() {
        println("init")
    }

    @Test
    fun junit5Test() {
        assertEquals(2, 1 + 1)
    }

    companion object {

        @BeforeAll
        @JvmStatic
        fun init() {
            println("init")
        }
    }
}