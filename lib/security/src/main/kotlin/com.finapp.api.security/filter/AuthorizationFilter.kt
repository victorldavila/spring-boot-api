package com.finapp.api.security.filter

import com.finapp.api.security.model.ProfilePermissionType
import org.springframework.web.reactive.function.server.HandlerFilterFunction
import org.springframework.web.reactive.function.server.ServerResponse

interface AuthorizationFilter: HandlerFilterFunction<ServerResponse, ServerResponse> {
    var roles: List<ProfilePermissionType>
}