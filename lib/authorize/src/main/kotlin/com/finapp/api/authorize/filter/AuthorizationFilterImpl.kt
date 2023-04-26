package com.finapp.api.authorize.filter

import com.finapp.api.security.model.ProfilePermissionType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFilterFunction
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AuthorizationFilterImpl(
    override var roles: List<ProfilePermissionType> = listOf(ProfilePermissionType.NONE)
) : AuthorizationFilter {

    @Autowired
    private lateinit var authorizationHelper: AuthorizationHelper

    override fun filter(request: ServerRequest, next: HandlerFunction<ServerResponse>): Mono<ServerResponse> {
        return authorizationHelper.getUserRoles(request)
            .map { authorizationHelper.containsAnyElementOnList(it, roles) }
            .flatMap { authorizationHelper.handleRoleAuthorization(it, request, next) }
    }
}