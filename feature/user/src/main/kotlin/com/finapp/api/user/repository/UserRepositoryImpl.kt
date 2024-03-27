package com.finapp.api.user.repository

import com.finapp.api.user_api.credential.data.Credential
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.repository.UserRepository
import org.springframework.data.mongodb.core.query.Update
import java.time.LocalDateTime

@Repository
class UserRepositoryImpl(
    private val template: ReactiveMongoTemplate
): UserRepository {

    override fun findUserById(userId: ObjectId): Mono<User> =
        template.findById(userId, User::class.java)

    override fun findUserByEmail(email: String): Mono<User> =
        template.findOne(getUserQueryByField(User.EMAIL_FIELD, email), User::class.java)

    override fun findUserByUsername(username: String): Mono<User> =
        template.findOne(getUserQueryByField("${User.CREDENTIAL_FIELD}.${Credential.USERNAME_FIELD}", username), User::class.java)

    override fun findAllUsers(): Flux<User> =
        template.findAll(User::class.java)

    override fun existsByUsername(username: String): Mono<Boolean> =
        template.findOne(getUserQueryByField("${User.CREDENTIAL_FIELD}.${Credential.USERNAME_FIELD}", username), User::class.java)
            .map { it is User }
            .switchIfEmpty(Mono.just(false))

    override fun existsByEmail(email: String): Mono<Boolean> =
        template.findOne(getUserQueryByField(User.EMAIL_FIELD, email), User::class.java)
            .map { it is User }
            .switchIfEmpty(Mono.just(false))

    override fun saveUser(user: User): Mono<User> =
        template.save(user)

    override fun updateUser(user: User): Mono<User> =
        template.findAndModify(
            Query(Criteria.where("_id").isEqualTo(user.id)),
            Update()
                .set(User.FIRST_NAME_FIELD, user.firstName)
                .set(User.LAST_NAME_FIELD, user.lastName)
                .set(User.EMAIL_FIELD, user.email)
                .set(User.LAST_MODIFIED_DATE_FIELD, LocalDateTime.now()),
            User::class.java
        )

    override fun deleteUser(user: User): Mono<DeleteResult> =
        template.remove(user)

    private fun <T>getUserQueryByField(field: String, value: T): Query =
        Query(
            Criteria.where(field)
                .isEqualTo(value)
        )

}