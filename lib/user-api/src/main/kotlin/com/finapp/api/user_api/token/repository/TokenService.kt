package com.finapp.api.user_api.token.repository

import com.finapp.api.user_api.data.User
import reactor.core.publisher.Mono

interface TokenService {
    fun generateNewToken(user: User): Mono<User>
    fun generateNewTokenByRefreshToken(refreshToken: String): Mono<User>
    fun deleteToken(accessToken: String): Mono<User>
}