package com.finapp.api.auth

import com.finapp.api.user_api.UserResponse
import com.finapp.api.user_api.repository.UserRepository
import jakarta.validation.Valid
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Mono

@Component
@Validated
class AuthServerImpl(
    private val userRepository: UserRepository
): AuthServer {
    override fun signUp(@Valid signUpRequest: SignUpRequest): Mono<UserResponse> {
        Mono.just(signUpRequest)
            .flatMap { usernameAlreadyExists(signUpRequest.credential.username) }
            .flatMap { emailAlreadyExists(signUpRequest.email) }
        return Mono.just(UserResponse("", "",  "",  "",  listOf()))
    }

    private fun emailAlreadyExists(email: String): Mono<Boolean> =
        userRepository.existsByEmail(email)
            .filter { it }
            .switchIfEmpty(Mono.error(Exception("")))

    private fun usernameAlreadyExists(username: String): Mono<Boolean> =
        userRepository.existsByUsername(username)
            .filter { it }
            .switchIfEmpty(Mono.error(Exception("")))

}