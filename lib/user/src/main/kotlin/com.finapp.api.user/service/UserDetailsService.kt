package com.finapp.api.user.service

import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono

interface UserDetailsService {
    fun loadUserByUsername(username: String): Mono<UserDetails>
}