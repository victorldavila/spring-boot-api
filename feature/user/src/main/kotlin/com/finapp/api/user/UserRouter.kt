package com.finapp.api.user

import com.finapp.api.authorize.filter.AuthorizationFilter
import com.finapp.api.authorize.filter.AuthorizationFilterImpl
import com.finapp.api.core.ApiRouter
import com.finapp.api.core.model.ProfilePermissionType
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
    fun userRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiGET("/v1/user/{userId}"), userHandler::getUser)
            .filter(getUserReadAuthorizationFilter())
        .andRoute(ApiRouter.apiGET("/v1/user"), userHandler::getAllUsers)
            .filter(getUserReadAuthorizationFilter())
        .andRoute(ApiRouter.apiPOST("/v1/user"), userHandler::createUser)
            .filter(getUserWriteAuthorizationFilter())
        .andRoute(ApiRouter.apiPUT("/v1/user"), userHandler::updateUser)
            .filter(getUserUpdateAuthorizationFilter())
        .andRoute(ApiRouter.apiDELETE("/v1/user"), userHandler::deleteUser)
            .filter(getUserDeleteAuthorizationFilter())

    private fun getUserReadAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(ProfilePermissionType.USER_READ, ProfilePermissionType.ADMIN_READ, ProfilePermissionType.SUPER_ADMIN_READ)
        return authorizationFilter
    }

    private fun getUserWriteAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(ProfilePermissionType.USER_WRITE, ProfilePermissionType.ADMIN_WRITE, ProfilePermissionType.SUPER_ADMIN_WRITE)
        return authorizationFilter
    }

    private fun getUserUpdateAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(ProfilePermissionType.USER_UPDATE, ProfilePermissionType.ADMIN_UPDATE, ProfilePermissionType.SUPER_ADMIN_UPDATE)
        return authorizationFilter
    }

    private fun getUserDeleteAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(ProfilePermissionType.USER_DELETE, ProfilePermissionType.ADMIN_DELETE, ProfilePermissionType.SUPER_ADMIN_DELETE)
        return authorizationFilter
    }
}