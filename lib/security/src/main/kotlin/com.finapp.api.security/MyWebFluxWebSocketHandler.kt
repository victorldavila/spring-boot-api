package com.finapp.api.security

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.EmitResult


@Component
class MyWebFluxWebSocketHandler(
    private val sink: Sinks.Many<Any>
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        val builder = UriComponentsBuilder.fromUri(session.handshakeInfo.uri)
        val queryParams = builder.build().queryParams.toSingleValueMap()
        val uniqueId = queryParams["email"]

        LOGGER.info("Websocket connected for id $uniqueId")

        sink.asFlux().subscribe(WebSocketSessionDataPublisher(uniqueId, session))

        val messageFlux = session
            .receive()
            .share()

        val input = messageFlux
            .filter { it.type == WebSocketMessage.Type.TEXT }
            .map { it.payloadAsText }
            .doOnNext {
                LOGGER.info("Recieved message from $uniqueId")
                val emitResult: EmitResult = sink.tryEmitNext(WSMessage(uniqueId, "This is message for $uniqueId"))
                LOGGER.info("Emit result status " + emitResult.name + " " + emitResult.isSuccess)
            }

        return Flux.merge(input).then()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MyWebFluxWebSocketHandler::class.java)
    }
}