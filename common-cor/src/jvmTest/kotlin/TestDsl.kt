import kotlinx.coroutines.runBlocking
import ru.otus.opinion.backend.common.cor.dsl.builders.LauncherBuilder
import ru.otus.opinion.backend.common.cor.dsl.chain
import ru.otus.opinion.backend.common.cor.dsl.worker
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDsl {

    data class TestContext(
        var value: Int = 0
    )

    @Test
    fun testTerminalWorker() {
        val worker = worker<TestContext> {
            on { value == 0 }
            execute {value++}
        }
        val ctx = TestContext(0)
        runBlocking {worker.process(ctx)}
        assertEquals(1, ctx.value)
    }

    @Test
    fun testLauncher() {
        val worker = chain<TestContext> {
            incrementer()
            chain {
                on { value == 1 }
                incrementer()
                chain {
                    on { value == 2 }
                    incrementer()
                    incrementer()
                }
                parallel {
                    incrementer()
                    incrementer()
                    worker {
                        on { value == 0 } // false
                        execute { value++ }
                    }
                }
            }
        }
        val ctx = TestContext(0)
        runBlocking { worker.process(ctx) }
        assertEquals(6, ctx.value)
    }

    private fun LauncherBuilder<TestContext>.incrementer() =
        worker {
            execute {
                value++
            }
        }
}