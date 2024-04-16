package com.finapp.api.auth.server

import com.finapp.api.auth.model.SignUpRequest
import com.finapp.api.auth.mapper.AuthMapper
import com.finapp.api.auth.model.CredentialRequest
import com.finapp.api.auth.model.TokenRequest
import com.finapp.api.core.error.BadRequestError
import com.finapp.api.kafka.BindKafkaProducer
import com.finapp.api.kafka.MessageProducerService
import com.finapp.api.kafka.ProductMessage
import com.finapp.api.role.repository.RoleRepository
import com.finapp.api.users.model.TokenResponse
import com.finapp.api.users.model.UserResponse
import com.finapp.api.users.data.User
import com.finapp.api.users.mapper.UserMapper
import com.finapp.api.users.repository.UserRepository
import com.finapp.api.users.service.TokenService
import kotlinx.coroutines.reactor.mono
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Component
class AuthServerImpl(
    private val messageProducerService: MessageProducerService,
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val authMapper: AuthMapper,
    private val userMapper: UserMapper,
    private val passwordEncoder: PasswordEncoder
): AuthServer {
    @Transactional(rollbackFor = [Exception::class])
    override fun signUp(signUpRequest: SignUpRequest): Mono<UserResponse> =
        Mono.just(signUpRequest)
            .flatMap { usernameAlreadyExists(signUpRequest.credential.username) }
            .flatMap { emailAlreadyExists(signUpRequest.email) }
            .map { authMapper.signUpRequestToUser(signUpRequest) }
            .flatMap { userRepository.saveUser(it) }
            .map { authMapper.getRoleByRoleType(it.id, signUpRequest.roleType) }
            .flatMap { roleRepository.saveRole(it) }
            .flatMap { userRepository.findUserById(it.userId) }
            .map { userMapper.userToUserResponse(it) }
            .doOnNext { mono { messageProducerService.sendMessage(ProductMessage(it.firstName + " " + it.lastName)) } }

    override fun signIn(credentialRequest: CredentialRequest): Mono<UserResponse> =
        Mono.just(credentialRequest)
            .flatMap { getUserByUsername(credentialRequest.username) }
            .filter { passwordEncoder.matches(credentialRequest.password, it.credential?.password) }
            .switchIfEmpty(Mono.error(BadRequestError("username or password are invalid")))
            .flatMap { tokenService.generateNewToken(it) }
            .map { userMapper.userToUserResponse(it, hasTokenInfo = true) }

    override fun signOut(tokenRequest: TokenRequest): Mono<Boolean> =
        Mono.just(tokenRequest)
            .flatMap { tokenService.deleteToken(it.token) }
            .map { it.token }
            .flatMapIterable { it }
            .filter { it.access == tokenRequest.token }
            .collectList()
            .map { it.size == 0 }

    override fun refresh(tokenRequest: TokenRequest): Mono<TokenResponse> =
        Mono.just(tokenRequest)
            .flatMap { tokenService.generateNewTokenByRefreshToken(it.token) }
            .map { userMapper.userToUserResponse(it, true) }
            .map { it.token }

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