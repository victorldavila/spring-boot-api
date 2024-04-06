package com.finapp.api.security

import com.finapp.api.core.websocket.WSMessage
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.util.function.Consumer


internal class WebSocketSessionDataPublisher(
    var uniqueId: String?,
    var session: WebSocketSession
) : Consumer<Any?> {

    override fun accept(o: Any?) {
        val message: WSMessage? = o as? WSMessage

        LOGGER.info("Message to be sent for id " + message?.uniqueId)

        if (uniqueId.equals(message?.uniqueId, ignoreCase = true)) {
            LOGGER.info("[Before Send]Message to be sent for id " + message?.uniqueId)
            session.send(Mono.just(session.textMessage(message?.message.toString()))).subscribe()
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(WebSocketSessionDataPublisher::class.java)
    }
}