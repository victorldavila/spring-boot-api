package com.finapp.api.security.repository

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository(
    private val authenticationManager: ReactiveAuthenticationManager,
    private val jwtServerAuthenticationConverter: ServerAuthenticationConverter
) : ServerSecurityContextRepository {

    override fun save(swe: ServerWebExchange?, sc: SecurityContext?): Mono<Void> {
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun load(swe: ServerWebExchange): Mono<SecurityContext> =
        jwtServerAuthenticationConverter.convert(swe)
            .flatMap {
                authenticationManager.authenticate(it)
                    .map { authenticate -> SecurityContextImpl(authenticate) }
            }
}