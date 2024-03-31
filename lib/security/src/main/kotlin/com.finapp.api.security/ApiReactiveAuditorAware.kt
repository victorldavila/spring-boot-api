package com.finapp.api.security

import com.finapp.api.security.jwt.JwtHelper
import com.finapp.api.user_api.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
internal class ApiReactiveAuditorAware : ReactiveAuditorAware<ObjectId> {
    @Autowired
    private lateinit var jwtHelper: JwtHelper

    override fun getCurrentAuditor(): Mono<ObjectId> {
        return ReactiveSecurityContextHolder.getContext()
            .map{ it.authentication }
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map { it as? String }
            .map{ jwtHelper.getUserIdFromJwtToken(it) }
            .map { ObjectId(it) }
    }
}