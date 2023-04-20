package com.finapp.api.security

import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepository {
    fun saveUser(user: User): Mono<User>
    fun deleteUser(user: User): Mono<DeleteResult>
    fun getUser(userId: ObjectId): Mono<User>
    fun findUserByEmail(email: String): Mono<User>
    fun findUserByUsername(username: String): Mono<User>
    fun existsByUsername(username: String): Mono<Boolean>
    fun existsByEmail(email: String): Mono<Boolean>
    fun getAllUsers(): Flux<User>
}