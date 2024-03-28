package com.finapp.api.user.token

import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.repository.UserRepository
import com.finapp.api.user_api.token.repository.TokenRepository
import com.finapp.api.user_api.token.data.Token
import org.bson.types.ObjectId

@Repository
class TokenRepositoryImpl(
    private val userRepository: UserRepository
): TokenRepository {

    override fun findTokenByUserId(userId: String): Flux<Token> =
        userRepository.findUserById(ObjectId(userId))
            .map { it.token }
            .flatMapIterable { it }

    override fun findTokenByAccessToken(user: User, accessToken: String): Mono<Token> =
        Mono.just(user)
            .map { it.token }
            .map { it.first { token -> token.access == accessToken } }
            .onErrorResume { Mono.empty() }

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