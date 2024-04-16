package com.finapp.api.kafka

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import java.util.function.Supplier

@Configuration
class BindKafkaProducer(
    private val messageProducerService: MessageProducerService
) {

    @Bean
    fun produceMessage(): Supplier<Flux<Message<ProductMessage>>> = Supplier {
        messageProducerService.messageProducer()
    }
}