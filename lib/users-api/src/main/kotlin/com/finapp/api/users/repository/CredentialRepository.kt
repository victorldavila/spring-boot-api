package com.finapp.api.users.repository

import com.finapp.api.users.data.Credential
import com.finapp.api.users.data.User
import reactor.core.publisher.Mono

interface CredentialRepository {
    fun findCredentialByUsername(username: String): Mono<Credential>
    fun saveCredential(user: User, credential: Credential): Mono<User>
}