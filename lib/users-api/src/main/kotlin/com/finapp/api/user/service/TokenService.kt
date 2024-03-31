package com.finapp.api.user.service

import com.finapp.api.user.data.User
import reactor.core.publisher.Mono

interface TokenService {
    fun generateNewToken(user: User): Mono<User>
    fun generateNewTokenByRefreshToken(refreshToken: String): Mono<User>
    fun deleteToken(accessToken: String): Mono<User>
}