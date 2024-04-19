package com.finapp.api.kafka

import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import java.util.function.Consumer

@Configuration
class BindKafkaConsumer {

    @Bean
    fun consumeMessage(): Consumer<Flux<Message<ProductMessage>>> = Consumer { stream ->
        stream.concatMap { msg ->
            mono {
                LOGGER.info(msg.payload.toString())
                //service.processProductMessage(msg.payload)
            }
        }.onErrorContinue { e, _ ->
            LOGGER.error(e.message, e)
        }.subscribe()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(BindKafkaConsumer::class.java)
    }
}