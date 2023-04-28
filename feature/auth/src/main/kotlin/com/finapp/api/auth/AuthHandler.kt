package com.finapp.api.auth

import com.finapp.api.auth.model.CredentialRequest
import com.finapp.api.auth.model.TokenRequest
import com.finapp.api.auth.model.SignUpRequest
import com.finapp.api.auth.server.AuthServer
import com.finapp.api.core.error.BadRequestError
import com.finapp.api.core.error.BaseHandler
import com.finapp.api.core.error.ValidationHandler
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AuthHandler(
    private val authServer: AuthServer,
    validationHandler: ValidationHandler
): BaseHandler(validationHandler) {
    fun signIn(serverRequest: ServerRequest): Mono<ServerResponse> =
        serverRequest.bodyToMono(CredentialRequest::class.java)
            .flatMap { authServer.signIn(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun signUp(serverRequest: ServerRequest): Mono<ServerResponse> =
        serverRequest.bodyToMono(SignUpRequest::class.java)
            .flatMap { authServer.signUp(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun signOut(serverRequest: ServerRequest): Mono<ServerResponse> =
        serverRequest.bodyToMono(TokenRequest::class.java)
            .flatMap { authServer.signOut(it) }
            .filter { it }
            .flatMap { ServerResponse.ok().build() }
            .switchIfEmpty(Mono.error(BadRequestError("invalid token")))
            .onErrorResume { errorResponse(it) }

    fun refresh(serverRequest: ServerRequest): Mono<ServerResponse> =
        serverRequest.bodyToMono(TokenRequest::class.java)
            .flatMap { authServer.refresh(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }
}