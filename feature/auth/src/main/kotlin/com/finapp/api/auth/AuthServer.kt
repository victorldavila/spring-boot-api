package com.finapp.api.auth

import com.finapp.api.user_api.UserResponse
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Mono

@Validated
interface AuthServer {
    fun signUp(@Valid signUpRequest: SignUpRequest): Mono<UserResponse>
}