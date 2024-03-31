package com.finapp.api.user.repository

import com.finapp.api.user.data.Credential
import com.finapp.api.user.data.User
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class CredentialRepositoryImpl(
    private val userRepository: UserRepository
): CredentialRepository {
    override fun findCredentialByUsername(username: String): Mono<Credential> =
        userRepository.findUserByUsername(username)
            .map { it.credential }

    override fun saveCredential(user: User, credential: Credential): Mono<User> =
        userRepository.saveUser(user.copy(credential = credential))
}