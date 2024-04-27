package com.finapp.api.products

import com.finapp.api.authorize.filter.AuthorizationFilterImpl
import com.finapp.api.core.ApiRouter
import com.finapp.api.core.model.PermissionType
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class ProductRouter(
    private val productHandler: ProductHandler,
    private val authorizationFilter: AuthorizationFilterImpl
) {
    @Bean
    fun productsRoutes(): RouterFunction<ServerResponse> =
        route(ApiRouter.apiGET("/v1/products/{productId}"), productHandler::getProductById)
            .filter(getProductReadAuthorizationFilter())
        .andRoute(ApiRouter.apiGET("/v1/products"), productHandler::getAllProducts)
            .filter(getProductReadAuthorizationFilter())
        .andRoute(ApiRouter.apiPOST("/v1/products"), productHandler::createProduct)
            .filter(getProductWriteAuthorizationFilter())
        .andRoute(ApiRouter.apiPUT("/v1/products/{productId}"), productHandler::updateProduct)
            .filter(getProductUpdateAuthorizationFilter())
        .andRoute(ApiRouter.apiDELETE("/v1/products/{productId}"), productHandler::deleteProduct)
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