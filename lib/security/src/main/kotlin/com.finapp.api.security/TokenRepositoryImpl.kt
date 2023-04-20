package com.finapp.api.security

import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class TokenRepositoryImpl(
    private val template: ReactiveMongoTemplate
) : TokenRepository {

    override fun findTokenByUsername(username: String?): Flux<Token> =
        template
            .findOne(
                Query(
                    Criteria
                        .where(User.USERNAME_FIELD)
                        .isEqualTo(username)
                ), User::class.java)
            .map { it.token }
            .flatMapIterable { it }

    override fun saveToken(token: Token): Mono<Token> =
        template.save(token)

    override fun deleteToken(token: Token): Mono<DeleteResult> =
        template.remove(token)
}