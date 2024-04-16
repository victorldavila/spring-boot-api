package com.finapp.api.users.repository

import com.finapp.api.users.data.User
import com.finapp.api.users.data.Token
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TokenRepository {
    fun findTokenByUserId(userId: String): Flux<Token>
    fun findTokenByAccessToken(user: User, accessToken: String): Mono<Token>
    fun findTokenByRefreshToken(user: User, refreshToken: String): Mono<Token>
    fun saveToken(user: User, token: Token): Mono<User>
    fun deleteToken(user: User, token: Token): Mono<User>
}