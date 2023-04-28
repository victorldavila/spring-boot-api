package com.finapp.api.auth.server

import com.finapp.api.auth.model.*
import com.finapp.api.user_api.UserResponse
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Mono

@Validated
interface AuthServer {
    @Validated(OnSignUp::class)
    fun signUp(@Valid signUpRequest: SignUpRequest): Mono<UserResponse>
    @Validated(OnSignIn::class)
    fun signIn(@Valid credentialRequest: CredentialRequest): Mono<UserResponse>
    fun signOut(@Valid signOutRequest: SignOutRequest): Mono<Boolean>
}