package com.finapp.api.mountable_products

import com.finapp.api.authorize.filter.AuthorizationFilterImpl
import com.finapp.api.core.ApiRouter
import com.finapp.api.core.model.PermissionType
import com.finapp.api.mountable_products.doc.MountableProductsDoc
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class MountableProductRouter(
    private val mountableProductHandler: MountableProductHandler,
    private val authorizationFilter: AuthorizationFilterImpl
) {
    @Bean
    @MountableProductsDoc
    fun mountableProductsRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiGET("/v1/products/steps/{mountableStepId}"), mountableProductHandler::getMountableStepById)
            .filter(getProductReadAuthorizationFilter())
        .andRoute(ApiRouter.apiGET("/v1/products/{productId}/steps"), mountableProductHandler::getAllMountableSteps)
            .filter(getProductReadAuthorizationFilter())
        .andRoute(ApiRouter.apiPOST("/v1/products/{productId}/steps"), mountableProductHandler::createMountableStep)
            .filter(getProductWriteAuthorizationFilter())
        .andRoute(ApiRouter.apiPUT("/v1/products/steps/{mountableStepId}"), mountableProductHandler::completeUpdateMountableStep)
            .filter(getProductUpdateAuthorizationFilter())
        .andRoute(ApiRouter.apiPATCH("/v1/products/steps/{mountableStepId}"), mountableProductHandler::partialUpdateMountableStep)
            .filter(getProductUpdateAuthorizationFilter())
        .andRoute(ApiRouter.apiDELETE("/v1/products/steps/{mountableStepId}"), mountableProductHandler::deleteMountableStep)
            .filter(getProductsDeleteAuthorizationFilter())
        .andRoute(ApiRouter.apiGET("/v1/products/steps/items/{mountableItemId}"), mountableProductHandler::getMountableItemById)
            .filter(getProductReadAuthorizationFilter())
        .andRoute(ApiRouter.apiGET("/v1/products/steps/{mountableStepId}/items"), mountableProductHandler::getAllMountableItems)
            .filter(getProductReadAuthorizationFilter())
        .andRoute(ApiRouter.apiPOST("/v1/products/steps/{mountableStepId}/items"), mountableProductHandler::createMountableItem)
            .filter(getProductWriteAuthorizationFilter())
        .andRoute(ApiRouter.apiPUT("/v1/products/steps/items/{mountableItemId}"), mountableProductHandler::completeUpdateMountableItem)
            .filter(getProductUpdateAuthorizationFilter())
        .andRoute(ApiRouter.apiPATCH("/v1/products/steps/items/{mountableItemId}"), mountableProductHandler::partialUpdateMountableItem)
            .filter(getProductUpdateAuthorizationFilter())
        .andRoute(ApiRouter.apiDELETE("/v1/products/steps/items/{mountableItemId}"), mountableProductHandler::deleteMountableItem)
            .filter(getProductsDeleteAuthorizationFilter())


    private fun getProductReadAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(PermissionType.PRODUCT_READ, PermissionType.ADMIN_READ, PermissionType.SUPER_ADMIN_READ)
        return authorizationFilter
    }

    private fun getProductWriteAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(PermissionType.PRODUCT_WRITE, PermissionType.ADMIN_WRITE, PermissionType.SUPER_ADMIN_WRITE)
        return authorizationFilter
    }

    private fun getProductUpdateAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(PermissionType.PRODUCT_UPDATE, PermissionType.ADMIN_UPDATE, PermissionType.SUPER_ADMIN_UPDATE)
        return authorizationFilter
    }

    private fun getProductsDeleteAuthorizationFilter(): AuthorizationFilterImpl {
        authorizationFilter.roles = listOf(PermissionType.PRODUCT_DELETE, PermissionType.ADMIN_DELETE, PermissionType.SUPER_ADMIN_DELETE)
        return authorizationFilter
    }
}