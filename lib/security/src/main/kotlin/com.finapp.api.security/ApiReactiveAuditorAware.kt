package com.finapp.api.security

import com.finapp.api.security.jwt.JwtHelper
import com.finapp.api.user_api.data.AuditingUser
import com.finapp.api.user_api.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
internal class ApiReactiveAuditorAware : ReactiveAuditorAware<AuditingUser> {
    @Autowired
    private lateinit var jwtHelper: JwtHelper

    @Autowired
    private lateinit var userRepository: UserRepository
    override fun getCurrentAuditor(): Mono<AuditingUser> {
        return ReactiveSecurityContextHolder.getContext()
            .map{ it.authentication }
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map { it as? String }
            .map{ jwtHelper.getUserIdFromJwtToken(it) }
            .flatMap { userRepository.findUserById(ObjectId(it)) }
            .map { AuditingUser(it.id, "${it.firstName} ${it.lastName}") }
    }
}