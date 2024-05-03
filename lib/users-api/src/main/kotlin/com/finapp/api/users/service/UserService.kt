package com.finapp.api.users.service

import com.finapp.api.core.validation.*
import com.finapp.api.users.model.UserArg
import com.finapp.api.users.model.UserParam
import com.finapp.api.users.model.UserRequest
import com.finapp.api.users.model.UserResponse
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface UserService {
    fun getAllUsers(): Flux<UserResponse>
    @Validated(OnRead::class)
    fun getUserById(@Valid userParam: UserParam): Mono<UserResponse>
    @Validated(OnUpdate::class)
    fun completeUpdateUser(@Valid userArg: UserArg): Mono<UserResponse>
    @Validated(OnPartialUpdate::class)
    fun partialUpdateUser(@Valid userArg: UserArg): Mono<UserResponse>
    @Validated(OnCreate::class)
    fun createUser(@Valid userRequest: UserRequest): Mono<UserResponse>
    @Validated(OnDelete::class)
    fun deleteUser(@Valid userArg: UserArg): Mono<Boolean>
}