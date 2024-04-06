package com.finapp.api.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping


@Configuration
class WebsocketConfiguration {
    @Autowired
    private val myWebFluxWebSocketHandler: MyWebFluxWebSocketHandler? = null

    @Bean
    fun handlerMapping(): HandlerMapping =
        SimpleUrlHandlerMapping(mapOf(
            "/uppercase" to myWebFluxWebSocketHandler
        ), 1)
}