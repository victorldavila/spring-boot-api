package com.finapp.api.user_api.service

import com.finapp.api.core.validation.OnCreate
import com.finapp.api.core.validation.OnUpdate
import com.finapp.api.user_api.model.UserRequest
import com.finapp.api.user_api.model.UserResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface UserService {
    fun getAllUsers(): Flux<UserResponse>
    fun getUserById(userId: String): Mono<UserResponse>
    @Validated(OnUpdate::class)
    fun updateUser(@Valid userRequest: UserRequest): Mono<UserResponse>
    @Validated(OnCreate::class)
    fun createUser(@Valid userRequest: UserRequest): Mono<UserResponse>
    fun deleteUser(@Valid @NotEmpty(message = "user id must not be empty") userId: String): Mono<Boolean>
}