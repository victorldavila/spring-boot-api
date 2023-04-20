package com.finapp.api.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty


@Component
class UserDetailsServiceImpl(
    val userRepository: UserRepository
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        return userRepository.findUserByUsername(username)
            .map { UserDetailsImpl.build(it) }
            .switchIfEmpty { throw UsernameNotFoundException("User Not Found with username: $username") }
            .block()
    }
}