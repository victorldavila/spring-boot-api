package com.finapp.api.auth

import com.finapp.api.user_api.UserResponse
import com.finapp.api.user_api.repository.UserRepository
import jakarta.validation.Valid
import jakarta.validation.executable.ValidateOnExecution
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Mono

@Component
//@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
class AuthServerImpl(
    private val userRepository: UserRepository,
    private val authMapper: AuthMapper
): AuthServer {
    override fun signUp(signUpRequest: SignUpRequest): Mono<UserResponse> =
        Mono.just(signUpRequest)
            .flatMap { usernameAlreadyExists(signUpRequest.credential.username) }
            .flatMap { emailAlreadyExists(signUpRequest.email) }
            .map { authMapper.signUpRequestToUser(signUpRequest) }
            .flatMap { userRepository.saveUser(it) }
            .map { authMapper.userToUserResponse(it) }

    private fun emailAlreadyExists(email: String): Mono<Boolean> =
        userRepository.existsByEmail(email)
            .filter { !it }
            .switchIfEmpty(Mono.error(BadRequestError("email already in use")))

    private fun usernameAlreadyExists(username: String): Mono<Boolean> =
        userRepository.existsByUsername(username)
            .filter { !it }
            .switchIfEmpty(Mono.error(BadRequestError("username already in use")))

}