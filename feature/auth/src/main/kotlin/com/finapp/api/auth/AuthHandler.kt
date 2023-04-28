package com.finapp.api.auth

import jakarta.validation.ConstraintViolationException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AuthHandler(
    private val authServer: AuthServer
) {
    fun signIn(serverRequest: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok().body(BodyInserters.fromValue(""))

    fun signUp(serverRequest: ServerRequest): Mono<ServerResponse> =
        serverRequest.bodyToMono(SignUpRequest::class.java)
            .flatMap { authServer.signUp(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorHandling(it) }

    private fun errorHandling(error: Throwable) = when(error) {
        is ConstraintViolationException -> ServerResponse.badRequest().body(BodyInserters.fromValue(error.message))
        is BadRequestError -> ServerResponse.badRequest().body(BodyInserters.fromValue(error.message))
        else -> throw error
    }
}