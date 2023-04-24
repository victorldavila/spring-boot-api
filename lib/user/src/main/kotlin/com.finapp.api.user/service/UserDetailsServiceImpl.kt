package com.finapp.api.user.service

import com.finapp.api.user.UserDetailsImpl
import com.finapp.api.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
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