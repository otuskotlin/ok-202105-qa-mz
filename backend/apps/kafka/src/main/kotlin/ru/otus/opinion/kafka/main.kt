package ru.otus.opinion.kafka

fun main() {
    val config = KafkaConfig()
    val controller = KafkaController(config)
    controller.run()
}