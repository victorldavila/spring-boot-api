package com.finapp.api.kafka

import kotlinx.coroutines.reactor.mono
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Flux

@Configuration
class BindKafkaSingleStream(
    private val singleStreamService: SingleStreamService
) {

    @Bean
    fun enrichAndSendToRabbit(): java.util.function.Function<Flux<Message<ProductMessage>>, Flux<Message<InventoryInfo>>> =
        java.util.function.Function { stream ->
            stream.concatMap { message ->
                mono { singleStreamService.enrichMessage(message.payload) }
            }.map {
                MessageBuilder
                    .withPayload(it)
                    .build()
            }
        }
}