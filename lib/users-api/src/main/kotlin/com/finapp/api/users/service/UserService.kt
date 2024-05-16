package com.finapp.api.users.service

import com.finapp.api.core.validation.*
import com.finapp.api.users.model.UserArg
import com.finapp.api.users.model.UserParam
import com.finapp.api.users.model.UserRequest
import com.finapp.api.users.model.UserResponse
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface UserService {
    fun getAllUsers(): Flux<UserResponse>
    @Validated(OnRead::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getUserById(@Valid userParam: UserParam): Mono<UserResponse>
    @Validated(OnUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun completeUpdateUser(@Valid userArg: UserArg): Mono<UserResponse>
    @Validated(OnPartialUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun partialUpdateUser(@Valid userArg: UserArg): Mono<UserResponse>
    @Validated(OnCreate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_WRITE')")
    fun createUser(@Valid userRequest: UserRequest): Mono<UserResponse>
    @Validated(OnDelete::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_DELETE')")
    fun deleteUser(@Valid userArg: UserArg): Mono<Boolean>
}