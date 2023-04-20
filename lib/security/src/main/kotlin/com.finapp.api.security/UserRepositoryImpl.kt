package com.finapp.api.security

import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Repository
class UserRepositoryImpl(
    private val template: ReactiveMongoTemplate
): UserRepository {
    override fun saveUser(user: User): Mono<User> =
        template.save(user)

    override fun deleteUser(user: User): Mono<DeleteResult> =
        template.remove(user)

    override fun getUser(userId: ObjectId): Mono<User> =
        template.findById(userId, User::class.java)

    override fun findUserByEmail(email: String): Mono<User> =
        template.findOne(getUserQueryByField(User.EMAIL_FIELD, email), User::class.java)

    override fun existsByUsername(username: String): Mono<Boolean> =
        template.findOne(getUserQueryByField(User.USERNAME_FIELD, username), User::class.java)
            .map { it is User }
            .switchIfEmpty { Mono.just(false) }

    override fun existsByEmail(email: String): Mono<Boolean> =
        template.findOne(getUserQueryByField(User.EMAIL_FIELD, email), User::class.java)
            .map { it is User }
            .switchIfEmpty { Mono.just(false) }

    override fun findUserByUsername(username: String): Mono<User> =
        template.findOne(getUserQueryByField(User.USERNAME_FIELD, username), User::class.java)

    override fun getAllUsers(): Flux<User> =
        template
            .findAll(User::class.java)

    private fun <T>getUserQueryByField(field: String, value: T): Query =
        Query(
            Criteria.where(field)
                .isEqualTo(value)
        )

}