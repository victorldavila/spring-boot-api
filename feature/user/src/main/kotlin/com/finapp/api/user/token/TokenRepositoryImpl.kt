package com.finapp.api.user.token

import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.repository.UserRepository
import com.finapp.api.user_api.token.repository.TokenRepository
import com.finapp.api.user_api.token.data.Token

@Repository
class TokenRepositoryImpl(
    private val userRepository: UserRepository
): TokenRepository {

    override fun findTokenByUsername(username: String): Flux<Token> =
        userRepository.findUserByUsername(username)
            .map { it.token }
            .flatMapIterable { it }

    override fun findTokenByAccessToken(user: User, accessToken: String): Mono<Token> =
        Mono.just(user)
            .map { it.token }
            .map { it.first { token -> token.access == accessToken } }

    override fun findTokenByRefreshToken(user: User, refreshToken: String): Mono<Token> =
        Mono.just(user)
            .map { it.token }
            .map { it.first { token -> token.refresh == refreshToken } }
            .onErrorResume { Mono.empty() }

    override fun saveToken(user: User, token: Token): Mono<User> =
        userRepository.saveUser(user.copy(token = user.token.toMutableList().apply { add(token) }))

    override fun deleteToken(user: User, token: Token): Mono<User> =
        userRepository.saveUser(user.copy(token = user.token.toMutableList().apply { remove(token) }))
}