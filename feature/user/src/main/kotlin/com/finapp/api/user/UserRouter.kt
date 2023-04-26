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
class UserRouter(private val userHandler: UserHandler) {
    @Bean
    fun userRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiGET("/v1/user/{userId}"), userHandler::getUser)
            .filter(getUserReadAuthorizationFilter()::filter)
        .andRoute(ApiRouter.apiGET("/v1/user"), userHandler::getUser)
            .filter(getUserReadAuthorizationFilter()::filter)
        .andRoute(ApiRouter.apiPOST("/v1/user"), userHandler::getUser)
            .filter(getUserWriteAuthorizationFilter()::filter)
        .andRoute(ApiRouter.apiPUT("/v1/user"), userHandler::getUser)
            .filter(getUserUpdateAuthorizationFilter()::filter)
        .andRoute(ApiRouter.apiDELETE("/v1/user"), userHandler::getUser)
            .filter(getUserDeleteAuthorizationFilter()::filter)

    private fun getUserReadAuthorizationFilter(): AuthorizationFilter =
        AuthorizationFilterImpl(listOf(ProfilePermissionType.USER_READ, ProfilePermissionType.ADMIN_READ, ProfilePermissionType.SUPER_ADMIN_READ))

    private fun getUserWriteAuthorizationFilter(): AuthorizationFilter =
        AuthorizationFilterImpl(listOf(ProfilePermissionType.USER_WRITE, ProfilePermissionType.ADMIN_WRITE, ProfilePermissionType.SUPER_ADMIN_WRITE))

    private fun getUserUpdateAuthorizationFilter(): AuthorizationFilter =
        AuthorizationFilterImpl(listOf(ProfilePermissionType.USER_UPDATE, ProfilePermissionType.ADMIN_UPDATE, ProfilePermissionType.SUPER_ADMIN_UPDATE))

    private fun getUserDeleteAuthorizationFilter(): AuthorizationFilter =
        AuthorizationFilterImpl(listOf(ProfilePermissionType.USER_DELETE, ProfilePermissionType.ADMIN_DELETE, ProfilePermissionType.SUPER_ADMIN_DELETE))

    companion object {
        const val ID_PARAM = "id"
    }
}