package com.finapp.api.user.repository

import com.finapp.api.user.data.User
import com.finapp.api.user.data.Token
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TokenRepository {
    fun findTokenByUserId(userId: String): Flux<Token>
    fun findTokenByAccessToken(user: User, accessToken: String): Mono<Token>
    fun findTokenByRefreshToken(user: User, refreshToken: String): Mono<Token>
    fun saveToken(user: User, token: Token): Mono<User>
    fun deleteToken(user: User, token: Token): Mono<User>
}