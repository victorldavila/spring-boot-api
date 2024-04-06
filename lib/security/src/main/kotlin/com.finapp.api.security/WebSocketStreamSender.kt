package com.finapp.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.Many


@Configuration
class WebSocketStreamSender {
    @Bean
    fun sink(): Many<Any> {
        return Sinks.many().replay().latest()
    }

    @Bean
    fun streamFlux(sink: Many<Any>): Flux<Any> {
        return sink.asFlux()
    }
}