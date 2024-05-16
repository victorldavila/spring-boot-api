package com.finapp.api.users

import com.finapp.api.core.ApiRouter
import com.finapp.api.users.doc.UsersDocV1
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class UserRouter(
    private val userHandler: UserHandler
) {
    @Bean
    @UsersDocV1
    fun userRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiGET("/v1/users/{userId}"), userHandler::getUser)
        .andRoute(ApiRouter.apiGET("/v1/users"), userHandler::getAllUsers)
        .andRoute(ApiRouter.apiPOST("/v1/users"), userHandler::createUser)
        .andRoute(ApiRouter.apiPUT("/v1/users/{userId}"), userHandler::completeUpdateUser)
        .andRoute(ApiRouter.apiPATCH("/v1/users/{userId}"), userHandler::partialUpdateUser)
        .andRoute(ApiRouter.apiDELETE("/v1/users/{userId}"), userHandler::deleteUser)
}