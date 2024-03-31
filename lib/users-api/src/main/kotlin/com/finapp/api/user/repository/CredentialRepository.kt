package com.finapp.api.user.repository

import com.finapp.api.user.data.Credential
import com.finapp.api.user.data.User
import reactor.core.publisher.Mono

interface CredentialRepository {
    fun findCredentialByUsername(username: String): Mono<Credential>
    fun saveCredential(user: User, credential: Credential): Mono<User>
}