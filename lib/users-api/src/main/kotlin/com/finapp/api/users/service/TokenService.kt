package com.finapp.api.users.service

import com.finapp.api.users.data.User
import reactor.core.publisher.Mono

interface TokenService {
    fun generateNewToken(user: User): Mono<User>
    fun generateNewTokenByRefreshToken(refreshToken: String): Mono<User>
    fun deleteToken(accessToken: String): Mono<User>
}