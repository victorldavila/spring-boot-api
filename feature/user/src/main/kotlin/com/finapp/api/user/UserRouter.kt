package com.finapp.api.user

import com.finapp.api.authorize.filter.AuthorizationFilterImpl
import com.finapp.api.core.ApiRouter
import com.finapp.api.core.model.PermissionType
import com.finapp.api.user_api.model.UserResponse
import com.finapp.api.user_api.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class UserRouter(
    private val userHandler: UserHandler,
    private val authorizationFilter: AuthorizationFilterImpl
) {
    @Bean
    @RouterOperations(
        RouterOperation(
            path = "/v1/users/{userId}",
            produces = [MediaType.APPLICATION_JSON_VALUE],
            method = [RequestMethod.PUT],
            beanClass = UserService::class,
            beanMethod = "updateUser",
            operation = Operation(
                operationId = "updateUser",
                responses = [
                    ApiResponse(
                        responseCode = "200",
                        description = "successful operation",
                        content = [Content(schema = Schema(implementation = UserResponse::class))]
                    ),
                    ApiResponse(responseCode = "400", description = "Invalid User ID supplied"),
                    ApiResponse(responseCode = "404", description = "User not found")
                ],
                parameters = [Parameter(`in` = ParameterIn.PATH, name = "userId")],
                requestBody = RequestBody(content = [Content()])
            )
        )
    )
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