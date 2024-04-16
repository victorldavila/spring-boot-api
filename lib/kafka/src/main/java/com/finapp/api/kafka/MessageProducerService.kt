package com.finapp.api.kafka

import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Component
class MessageProducerService {

    private val unicastProcessor = Sinks
        .many()
        .unicast()
        .onBackpressureBuffer<Message<ProductMessage>>()

    fun messageProducer(): Flux<Message<ProductMessage>> = unicastProcessor.asFlux()

    suspend fun sendMessage(productMessage: ProductMessage) {
        val message = MessageBuilder
            .withPayload(productMessage)
            .build()

        unicastProcessor.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST)
    }
}