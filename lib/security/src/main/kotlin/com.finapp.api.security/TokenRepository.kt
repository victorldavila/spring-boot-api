package com.finapp.api.security

import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TokenRepository {
    fun findTokenByUsername(username: String?): Flux<Token>
    fun saveToken(token: Token): Mono<Token>
    fun deleteToken(token: Token): Mono<DeleteResult>
}