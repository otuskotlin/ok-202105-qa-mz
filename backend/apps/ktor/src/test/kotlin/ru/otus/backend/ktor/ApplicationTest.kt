package ru.otus.backend.ktor

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.opinion.ktor.configs.AppConfig
import ru.otus.opinion.ktor.module
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun  testRoot() {
        withTestApplication({
            module(AppConfig.TEST_CONFIG)
        }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello, World!", response.content)
            }
        }
    }
}