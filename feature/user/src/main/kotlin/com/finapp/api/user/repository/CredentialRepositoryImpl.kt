package com.finapp.api.user.repository

import com.finapp.api.user_api.data.Credential
import com.finapp.api.user_api.repository.CredentialRepository
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.repository.UserRepository
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