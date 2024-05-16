package com.finapp.api.mountable_products

import com.finapp.api.core.ApiRouter
import com.finapp.api.mountable_products.doc.MountableProductsDoc
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class MountableProductRouter(
    private val mountableProductHandler: MountableProductHandler
) {
    @Bean
    @MountableProductsDoc
    fun mountableProductsRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiGET("/v1/products/steps/{mountableStepId}"), mountableProductHandler::getMountableStepById)
            .andRoute(ApiRouter.apiGET("/v1/products/{productId}/steps"), mountableProductHandler::getAllMountableSteps)
            .andRoute(ApiRouter.apiGET("/v1/products/steps/items/{mountableItemId}"), mountableProductHandler::getMountableItemById)
            .andRoute(ApiRouter.apiGET("/v1/products/steps/{mountableStepId}/items"), mountableProductHandler::getAllMountableItems)
            .andRoute(ApiRouter.apiPOST("/v1/products/{productId}/steps"), mountableProductHandler::createMountableStep)
            .andRoute(ApiRouter.apiPOST("/v1/products/steps/{mountableStepId}/items"), mountableProductHandler::createMountableItem)
            .andRoute(ApiRouter.apiPUT("/v1/products/steps/{mountableStepId}"), mountableProductHandler::completeUpdateMountableStep)
            .andRoute(ApiRouter.apiPUT("/v1/products/steps/items/{mountableItemId}"), mountableProductHandler::completeUpdateMountableItem)
            .andRoute(ApiRouter.apiPATCH("/v1/products/steps/{mountableStepId}"), mountableProductHandler::partialUpdateMountableStep)
            .andRoute(ApiRouter.apiPATCH("/v1/products/steps/items/{mountableItemId}"), mountableProductHandler::partialUpdateMountableItem)
            .andRoute(ApiRouter.apiDELETE("/v1/products/steps/{mountableStepId}"), mountableProductHandler::deleteMountableStep)
            .andRoute(ApiRouter.apiDELETE("/v1/products/steps/items/{mountableItemId}"), mountableProductHandler::deleteMountableItem)
}