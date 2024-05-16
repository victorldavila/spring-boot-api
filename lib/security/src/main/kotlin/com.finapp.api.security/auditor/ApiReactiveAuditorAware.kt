package com.finapp.api.security.auditor

import com.finapp.api.security.token.TokenGenerator
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ApiReactiveAuditorAware : ReactiveAuditorAware<ObjectId> {
    @Autowired
    private lateinit var tokenGenerator: TokenGenerator

    override fun getCurrentAuditor(): Mono<ObjectId?> {
        return ReactiveSecurityContextHolder.getContext()
            .map { it.authentication }
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map { it as? Jwt }
            .map { it?.tokenValue }
            .flatMap { tokenGenerator.getUserIdByAccessToken(it) }
            .map { ObjectId(it) }
    }
}