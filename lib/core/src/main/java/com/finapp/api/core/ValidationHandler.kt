package com.finapp.api.core

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors


//@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class ValidationHandler : WebExceptionHandler {
    private val objectMapper: ObjectMapper? = null

    //@SneakyThrows
    override fun handle(exchange: ServerWebExchange, throwable: Throwable): Mono<Void> {
        return if (throwable is WebExchangeBindException) {
            val errors = getValidationErrors(throwable)
            exchange.response.setStatusCode(HttpStatus.BAD_REQUEST)
            exchange.response.headers.contentType = MediaType.APPLICATION_JSON
            writeResponse(exchange, objectMapper!!.writeValueAsBytes(errors))
        } else {
            Mono.error(throwable)
        }
    }

    private fun getValidationErrors(validationEx: WebExchangeBindException): Map<String, Any>? {
        return validationEx.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "") }
    }

    private fun writeResponse(exchange: ServerWebExchange, responseBytes: ByteArray): Mono<Void> {
        return exchange.response.writeWith(Mono.just(exchange.response.bufferFactory().wrap(responseBytes)))
    }
}