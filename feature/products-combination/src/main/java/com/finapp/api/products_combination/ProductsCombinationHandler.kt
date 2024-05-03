package com.finapp.api.products_combination

import com.finapp.api.core.error.BaseHandler
import com.finapp.api.core.error.ValidationHandler
import com.finapp.api.core.tryGetPathVariable
import com.finapp.api.products_combination.model.*
import com.finapp.api.products_combination.service.ProductCombinationItemsService
import com.finapp.api.products_combination.service.ProductsCombinationService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ProductsCombinationHandler(
    private val productsCombinationService: ProductsCombinationService,
    private val productCombinationItemsService: ProductCombinationItemsService,
    validationHandler: ValidationHandler
): BaseHandler(validationHandler) {

    fun getProductCombinationById(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getProductsCombinationParam(it) }
            .flatMap { productsCombinationService.getProductsCombinationById(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun getAllProductsCombination(serverRequest: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .body(productsCombinationService.getAllProductsCombination(), ProductsCombinationResponse::class.java)
            .onErrorResume { errorResponse(it) }

    fun completeUpdateProductCombination(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateProductCombinationParam(serverRequest)
            .flatMap { productsCombinationService.updateProductsCombination(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun partialUpdateProductCombination(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateProductCombinationParam(serverRequest)
            .flatMap { productsCombinationService.updateProductsCombination(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    private fun updateProductCombinationParam(serverRequest: ServerRequest): Mono<ProductsCombinationArg> =
        Mono.just(serverRequest)
            .flatMap { serverRequest ->
                serverRequest.bodyToMono(ProductsCombinationRequest::class.java)
                    .map { ProductsCombinationArg(getProductsCombinationParam(serverRequest), it) }
            }

    fun createProductCombination(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { it.bodyToMono(ProductsCombinationRequest::class.java) }
            .flatMap { productsCombinationService.createProductsCombination(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun deleteProductCombination(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getProductsCombinationParam(it) }
            .flatMap { productsCombinationService.deleteProductsCombination(it) }
            .flatMap { ServerResponse.noContent().build() }
            .onErrorResume { errorResponse(it) }

    fun getProductCombinationItemById(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getProductCombinationItemsParam(it) }
            .flatMap { productCombinationItemsService.getProductCombinationItemById(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun getAllProductCombinationItemsByProductCombinationId(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getProductCombinationItemsParam(it) }
            .flatMap {
                ServerResponse.ok()
                    .body(
                        productCombinationItemsService.getAllProductCombinationItemsByProductCombinationId(it),
                        ProductCombinationItemResponse::class.java
                    )
            }
            .onErrorResume { errorResponse(it) }

    fun completeUpdateProductCombinationItem(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateProductCombinationItemArg(serverRequest)
            .flatMap { productCombinationItemsService.completeUpdateProductCombinationItem(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun partialUpdateProductCombinationItem(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateProductCombinationItemArg(serverRequest)
            .flatMap { productCombinationItemsService.partialUpdateProductCombinationItem(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    private fun updateProductCombinationItemArg(serverRequest: ServerRequest): Mono<ProductCombinationItemsArg> =
        Mono.just(serverRequest)
            .flatMap { serverRequest ->
                serverRequest.bodyToMono(ProductCombinationItemRequest::class.java)
                    .map { ProductCombinationItemsArg(getProductCombinationItemsParam(serverRequest), it) }
            }

    fun createProductCombinationItem(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { it.bodyToMono(ProductCombinationItemRequest::class.java) }
            .flatMap {
                productCombinationItemsService.createProductCombinationItem(
                    ProductCombinationItemsArg(getProductCombinationItemsParam(serverRequest), it)
                )
            }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun deleteProductCombinationItem(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getProductCombinationItemsParam(it) }
            .flatMap { productCombinationItemsService.deleteProductCombinationItem(it) }
            .flatMap { ServerResponse.noContent().build() }
            .onErrorResume { errorResponse(it) }

    private fun getProductsCombinationParam(serverRequest: ServerRequest): ProductsCombinationParam {
        val productCombinationId = serverRequest.pathVariable("productCombinationId")

        return ProductsCombinationParam(productCombinationId)
    }
    private fun getProductCombinationItemsParam(serverRequest: ServerRequest): ProductCombinationItemsParam {
        val productCombinationId = serverRequest.tryGetPathVariable("productCombinationId")
        val productCombinationItemId = serverRequest.tryGetPathVariable("productCombinationItemId")

        return ProductCombinationItemsParam(productCombinationId, productCombinationItemId)
    }

}