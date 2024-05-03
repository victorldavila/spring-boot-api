package com.finapp.api.users

import com.finapp.api.core.error.BaseHandler
import com.finapp.api.core.error.ValidationHandler
import com.finapp.api.users.model.UserArg
import com.finapp.api.users.model.UserParam
import com.finapp.api.users.model.UserRequest
import com.finapp.api.users.model.UserResponse
import com.finapp.api.users.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UserHandler(
    private val userService: UserService,
    validationHandler: ValidationHandler
): BaseHandler(validationHandler) {

    fun getUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getUserParam(it) }
            .flatMap { userService.getUserById(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun getAllUsers(serverRequest: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .body(userService.getAllUsers(), UserResponse::class.java)

    fun completeUpdateUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateUserArg(serverRequest)
            .flatMap { userService.updateUser(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun partialUpdateUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateUserArg(serverRequest)
            .flatMap { userService.updateUser(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    private fun updateUserArg(serverRequest: ServerRequest): Mono<UserArg> =
        Mono.just(serverRequest)
            .flatMap { serverRequest ->
                serverRequest.bodyToMono(UserRequest::class.java)
                    .map { UserArg(getUserParam(serverRequest), it) }
            }

    fun createUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { it.bodyToMono(UserRequest::class.java) }
            .flatMap { userService.createUser(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun deleteUser(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { UserArg(getUserParam(it)) }
            .flatMap { userService.deleteUser(it) }
            .flatMap { ServerResponse.noContent().build() }
            .onErrorResume { errorResponse(it) }

    private fun getUserParam(serverRequest: ServerRequest): UserParam {
        val userId = serverRequest.pathVariable("userId")

        return UserParam(userId)
    }
}