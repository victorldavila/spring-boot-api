package com.finapp.api.user

import com.finapp.api.core.error.BaseHandler
import com.finapp.api.core.error.ValidationHandler
import com.finapp.api.user_api.model.UserRequest
import com.finapp.api.user_api.model.UserResponse
import com.finapp.api.user_api.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
class UserHandler(
    private val userService: UserService,
    validationHandler: ValidationHandler
): BaseHandler(validationHandler) {

    fun getUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { it.pathVariable("userId") }
            .flatMap { userService.getUserById(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun getAllUsers(serverRequest: ServerRequest): Mono<ServerResponse> =
        ServerResponse
            .ok()
            .body(userService.getAllUsers(), UserResponse::class.java)

    fun updateUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { it.bodyToMono(UserRequest::class.java) }
            .flatMap { userService.updateUser(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun createUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { it.bodyToMono(UserRequest::class.java) }
            .flatMap { userService.createUser(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun deleteUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { it.pathVariable("userId") }
            .flatMap { userService.deleteUser(it) }
            .flatMap { ServerResponse.noContent().build() }
            .onErrorResume { errorResponse(it) }
}