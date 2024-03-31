package com.finapp.api.user.service

import com.finapp.api.core.validation.OnCreate
import com.finapp.api.core.validation.OnDelete
import com.finapp.api.core.validation.OnRead
import com.finapp.api.core.validation.OnUpdate
import com.finapp.api.user.model.UserArg
import com.finapp.api.user.model.UserParam
import com.finapp.api.user.model.UserRequest
import com.finapp.api.user.model.UserResponse
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
    fun updateUser(@Valid userArg: UserArg): Mono<UserResponse>
    @Validated(OnCreate::class)
    fun createUser(@Valid userRequest: UserRequest): Mono<UserResponse>
    @Validated(OnDelete::class)
    fun deleteUser(@Valid userArg: UserArg): Mono<Boolean>
}