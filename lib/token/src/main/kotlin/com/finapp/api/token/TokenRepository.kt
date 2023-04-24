package com.finapp.api.token

import com.finapp.api.token.data.Token
import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TokenRepository {
    fun findTokenByUsername(username: String?): Flux<Token>
    fun saveToken(token: Token): Mono<Token>
    fun deleteToken(token: Token): Mono<DeleteResult>
}