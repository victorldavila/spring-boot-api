package com.finapp.api.user_api.repository

import com.finapp.api.user_api.data.Credential
import com.finapp.api.user_api.data.User
import reactor.core.publisher.Mono

interface CredentialRepository {
    fun findCredentialByUsername(username: String): Mono<Credential>
    fun saveCredential(user: User, credential: Credential): Mono<User>
}