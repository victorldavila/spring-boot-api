package com.finapp.api.auth

import com.finapp.api.core.ApiRouter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class AuthRouter(private val authHandler: AuthHandler) {
    @Bean
    fun authRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiPOST("/v1/auth/signin"), authHandler::signIn)
            .andRoute(ApiRouter.apiPOST("/v1/auth/signup"), authHandler::signUp)
            .andRoute(ApiRouter.apiPOST("/v1/auth/refresh"), authHandler::signIn)
            .andRoute(ApiRouter.apiPOST("/v1/auth/signout"), authHandler::signIn)
}