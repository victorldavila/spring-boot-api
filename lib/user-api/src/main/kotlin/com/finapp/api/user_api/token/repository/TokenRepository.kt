package com.finapp.api.user_api.token.repository

import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.token.data.Token
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TokenRepository {
    fun findTokenByUsername(username: String): Flux<Token>
    fun saveToken(user: User, token: Token): Mono<User>
    fun deleteToken(user: User, token: Token): Mono<User>
}