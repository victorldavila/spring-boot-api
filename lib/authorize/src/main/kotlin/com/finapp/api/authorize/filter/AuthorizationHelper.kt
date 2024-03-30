package com.finapp.api.authorize.filter

import com.finapp.api.core.model.PermissionType
import com.finapp.api.core.model.ProfilePermissionType
import com.finapp.api.core.model.Security
import com.finapp.api.security.jwt.JwtHelper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AuthorizationHelper(
    private val jwtHelper: JwtHelper
) {
    fun getUserRoles(request: ServerRequest): Mono<List<GrantedAuthority>> =
        Mono.just(request)
            .map { it.headers().firstHeader(HttpHeaders.AUTHORIZATION) }
            .map { it?.substring(Security.Header.AUTH_PREFIX.length) }
            .map { jwtHelper.getRoles(it) }

    internal fun handleRoleAuthorization(hasRole: Boolean, request: ServerRequest, next: HandlerFunction<ServerResponse>) = if (hasRole) {
        next.handle(request)
    } else {
        ServerResponse.status(HttpStatus.FORBIDDEN).build()
    }

    fun containsAnyElementOnList(userRoles: List<GrantedAuthority>, acceptableRoles: List<PermissionType>): Boolean {
        var userHasPermission = false

        acceptableRoles.forEach {
            if (userRoles.contains(it.getGrantedAuthority())) {
                userHasPermission = true
            }
        }

        return userHasPermission
    }
}