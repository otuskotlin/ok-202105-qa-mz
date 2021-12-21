package ru.otus.opinion.logging

import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertTrue

class LoggerTest {
    private val logger = coreLogger(this::class.java)
    private val loggerId = "test-logger"

    @Test
    fun `log action`() {
        val output = invokeLogger {
            println("Some action")
        }.toString()

        assertTrue(Regex(".*$loggerId started.*").containsMatchIn(output))
        assertTrue(output.contains("Some action"))
        assertTrue(Regex(".*$loggerId finished.*").containsMatchIn(output))
    }

    @Test
    fun `log exception`() {
        val output = invokeLogger {
            throw RuntimeException("Some action")
        }.toString()

        assertTrue(Regex(".*$loggerId.* started.*").containsMatchIn(output))
        assertTrue(Regex(".*$loggerId.* failed*").containsMatchIn(output))
    }

    private fun invokeLogger(block: suspend () -> Unit): ByteArrayOutputStream {
        val outputStreamCaptor = outputStreamCaptor()
        try {
            runBlocking {
                logger.logAction(loggerId, action = block)
            }
        } catch (ignore: RuntimeException) {
        }
        return outputStreamCaptor
    }

    private fun outputStreamCaptor(): ByteArrayOutputStream {
        return ByteArrayOutputStream().apply {
            /*
             * Send system output to print stream over byte array.
             * Normally it is sent to System.out: System.setOut(System.out)
             */
            System.setOut(PrintStream(this))
        }
    }
}