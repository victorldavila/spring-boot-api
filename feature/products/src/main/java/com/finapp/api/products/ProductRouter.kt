package com.finapp.api.products

import com.finapp.api.core.ApiRouter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class ProductRouter(
    private val productHandler: ProductHandler
) {
    @Bean
    fun productsRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiGET("/v1/products/{productId}"), productHandler::getProductById)
        .andRoute(ApiRouter.apiGET("/v1/products"), productHandler::getAllProducts)
        .andRoute(ApiRouter.apiPOST("/v1/products"), productHandler::createProduct)
        .andRoute(ApiRouter.apiPUT("/v1/products/{productId}"), productHandler::completeUpdateProduct)
        .andRoute(ApiRouter.apiPATCH("/v1/products/{productId}"), productHandler::partialUpdateProduct)
        .andRoute(ApiRouter.apiImagePUT("/v1/products/{productId}/images"), productHandler::updateProductImage)
        .andRoute(ApiRouter.apiDELETE("/v1/products/{productId}"), productHandler::deleteProduct)
}