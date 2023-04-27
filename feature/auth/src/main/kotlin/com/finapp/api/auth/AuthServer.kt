package com.finapp.api.auth

import com.finapp.api.user_api.UserResponse
import reactor.core.publisher.Mono

interface AuthServer {
    fun signUp(signUpRequest: SignUpRequest): Mono<UserResponse>
}