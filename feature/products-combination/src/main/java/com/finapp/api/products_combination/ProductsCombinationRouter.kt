package com.finapp.api.products_combination

import com.finapp.api.core.ApiRouter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class ProductsCombinationRouter(
    private val productsCombinationHandler: ProductsCombinationHandler
) {
    @Bean
    fun productsCombinationRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiGET("/v1/products/combination/{productCombinationId}"), productsCombinationHandler::getProductCombinationById)
            .andRoute(ApiRouter.apiGET("/v1/products/combination/{productCombinationId}/items"), productsCombinationHandler::getAllProductCombinationItemsByProductCombinationId)
            .andRoute(ApiRouter.apiGET("/v1/products/combination/items/{productCombinationItemId}"), productsCombinationHandler::getProductCombinationItemById)
            .andRoute(ApiRouter.apiGET("/v1/products/combination"), productsCombinationHandler::getAllProductsCombination)
            .andRoute(ApiRouter.apiPOST("/v1/products/combination"), productsCombinationHandler::createProductCombination)
            .andRoute(ApiRouter.apiPOST("/v1/products/combination/{productCombinationId}/items"), productsCombinationHandler::createProductCombinationItem)
            .andRoute(ApiRouter.apiPUT("/v1/products/combination/{productCombinationId}"), productsCombinationHandler::completeUpdateProductCombination)
            .andRoute(ApiRouter.apiPUT("/v1/products/combination/items/{productCombinationItemId}"), productsCombinationHandler::completeUpdateProductCombinationItem)
            .andRoute(ApiRouter.apiPATCH("/v1/products/combination/items/{productCombinationItemId}"), productsCombinationHandler::partialUpdateProductCombinationItem)
            .andRoute(ApiRouter.apiPATCH("/v1/products/combination/{productCombinationId}"), productsCombinationHandler::partialUpdateProductCombination)
            .andRoute(ApiRouter.apiDELETE("/v1/products/combination/{productCombinationId}"), productsCombinationHandler::deleteProductCombination)
            .andRoute(ApiRouter.apiDELETE("/v1/products/combination/items/{productCombinationItemId}"), productsCombinationHandler::deleteProductCombinationItem)
}