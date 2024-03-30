package com.finapp.api.authorize.filter

import com.finapp.api.core.model.PermissionType
import com.finapp.api.core.model.ProfilePermissionType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFilterFunction
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AuthorizationFilterImpl(
    private val authorizationHelper: AuthorizationHelper
) : HandlerFilterFunction<ServerResponse, ServerResponse> {

    var roles: List<PermissionType> = listOf(PermissionType.NONE)

    override fun filter(request: ServerRequest, next: HandlerFunction<ServerResponse>): Mono<ServerResponse> {
        return authorizationHelper.getUserRoles(request)
            .map { authorizationHelper.containsAnyElementOnList(it, roles) }
            .flatMap { authorizationHelper.handleRoleAuthorization(it, request, next) }
    }
}