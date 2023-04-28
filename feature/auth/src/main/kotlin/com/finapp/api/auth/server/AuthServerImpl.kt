package com.finapp.api.auth.server

import com.finapp.api.auth.model.SignUpRequest
import com.finapp.api.auth.mapper.AuthMapper
import com.finapp.api.auth.model.CredentialRequest
import com.finapp.api.auth.model.SignOutRequest
import com.finapp.api.core.error.BadRequestError
import com.finapp.api.core.error.NotFoundError
import com.finapp.api.user_api.UserResponse
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.repository.UserRepository
import com.finapp.api.user_api.token.repository.TokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthServerImpl(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val authMapper: AuthMapper,
    private val passwordEncoder: PasswordEncoder
): AuthServer {
    override fun signUp(signUpRequest: SignUpRequest): Mono<UserResponse> =
        Mono.just(signUpRequest)
            .flatMap { usernameAlreadyExists(signUpRequest.credential.username) }
            .flatMap { emailAlreadyExists(signUpRequest.email) }
            .map { authMapper.signUpRequestToUser(signUpRequest) }
            .flatMap { userRepository.saveUser(it) }
            .map { authMapper.userToUserResponse(it) }

    override fun signIn(credentialRequest: CredentialRequest): Mono<UserResponse> =
        Mono.just(credentialRequest)
            .flatMap { getUserByUsername(credentialRequest.username) }
            .filter { passwordEncoder.matches(credentialRequest.password, it.credential.password) }
            .switchIfEmpty(Mono.error(BadRequestError("username or password are invalid")))
            .flatMap { tokenService.generateNewToken(it) }
            .map { authMapper.userToUserResponse(it, hasTokenInfo = true) }

    override fun signOut(signOutRequest: SignOutRequest): Mono<Boolean> =
        Mono.just(signOutRequest)
            .flatMap { tokenService.deleteToken(it.accessToken) }
            .map { it.token }
            .flatMapIterable { it }
            .filter { it.access == signOutRequest.accessToken }
            .collectList()
            .map { false }
            .switchIfEmpty(Mono.just(true))

    private fun getUserByUsername(username: String): Mono<User> =
        userRepository.findUserByUsername(username)
            .switchIfEmpty(Mono.error(BadRequestError("username or password are invalid")))

    private fun emailAlreadyExists(email: String): Mono<Boolean> =
        userRepository.existsByEmail(email)
            .filter { !it }
            .switchIfEmpty(Mono.error(BadRequestError("email already in use")))

    private fun usernameAlreadyExists(username: String): Mono<Boolean> =
        userRepository.existsByUsername(username)
            .filter { !it }
            .switchIfEmpty(Mono.error(BadRequestError("username already in use")))

}