package com.finapp.api.user

import com.finapp.api.authorize.filter.AuthorizationFilterImpl
import com.finapp.api.core.ApiRouter
import com.finapp.api.core.model.PermissionType
import com.finapp.api.user_api.doc.UserDocV1
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class UserRouter(
    private val userHandler: UserHandler,
    private val authorizationFilter: AuthorizationFilterImpl
) {
    @Bean
    @UserDocV1
    fun userRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiGET("/v1/users/{userId}"), userHandler::getUser)
            .filter(getUserReadAuthorizationFilter())
        .andRoute(ApiRouter.apiGET("/v1/users"), userHandler::getAllUsers)
            .filter(getUserReadAuthorizationFilter())
        .andRoute(ApiRouter.apiPOST("/v1/users"), userHandler::createUser)
            .filter(getUserWriteAuthorizationFilter())
        .andRoute(ApiRouter.apiPUT("/v1/users/{userId}"), userHandler::updateUser)
            .filter(getUserUpdateAuthorizationFilter())
        .andRoute(ApiRouter.apiDELETE("/v1/users/{userId}"), userHandler::deleteUser)
            .filter(getUserDeleteAuthorizationFilter())

    private fun getUserReadAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(PermissionType.USER_READ, PermissionType.ADMIN_READ, PermissionType.SUPER_ADMIN_READ)
        return authorizationFilter
    }

    private fun getUserWriteAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(PermissionType.USER_WRITE, PermissionType.ADMIN_WRITE, PermissionType.SUPER_ADMIN_WRITE)
        return authorizationFilter
    }

    private fun getUserUpdateAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(PermissionType.USER_UPDATE, PermissionType.ADMIN_UPDATE, PermissionType.SUPER_ADMIN_UPDATE)
        return authorizationFilter
    }

    private fun getUserDeleteAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(PermissionType.USER_DELETE, PermissionType.ADMIN_DELETE, PermissionType.SUPER_ADMIN_DELETE)
        return authorizationFilter
    }
}