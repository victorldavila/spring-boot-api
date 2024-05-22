package com.finapp.api.products

import com.finapp.api.core.error.BaseHandler
import com.finapp.api.core.error.ValidationHandler
import com.finapp.api.products.model.ProductArg
import com.finapp.api.products.model.ProductParam
import com.finapp.api.products.model.ProductRequest
import com.finapp.api.products.model.ProductResponse
import com.finapp.api.products.service.ProductService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import kotlin.jvm.optionals.getOrNull

@Component
class ProductHandler(
    private val productsService: ProductService,
    validationHandler: ValidationHandler
): BaseHandler(validationHandler) {

    fun getProductById(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getProductParam(it) }
            .flatMap { productsService.getProductById(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun getAllProducts(serverRequest: ServerRequest): Mono<ServerResponse> {
        val isFull = serverRequest.queryParam("isFull")

        return ServerResponse.ok()
            .body(productsService.getAllProducts(isFull.getOrNull().toBoolean()), ProductResponse::class.java)
            .onErrorResume { errorResponse(it) }
    }

    fun completeUpdateProduct(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateProductArg(serverRequest)
            .flatMap { productsService.completeUpdateProduct(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun partialUpdateProduct(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateProductArg(serverRequest)
            .flatMap { productsService.partialUpdateProduct(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    private fun updateProductArg(serverRequest: ServerRequest): Mono<ProductArg> =
        Mono.just(serverRequest)
            .flatMap {
                serverRequest.bodyToMono(ProductRequest::class.java)
                    .map { ProductArg(getProductParam(serverRequest), it) }
            }

    fun updateProductImage(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { it.multipartData() }
            .flatMap { productsService.updateProductImage(it["file"], getProductParam(serverRequest)) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun createProduct(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { it.bodyToMono(ProductRequest::class.java) }
            .flatMap { productsService.createProduct(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun deleteProduct(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { ProductArg(getProductParam(it)) }
            .flatMap { productsService.deleteProduct(it.productParam) }
            .flatMap { ServerResponse.noContent().build() }
            .onErrorResume { errorResponse(it) }

    private fun getProductParam(serverRequest: ServerRequest): ProductParam {
        val productId = serverRequest.pathVariable("productId")
        val isFull = serverRequest.queryParam("isFull")

        return ProductParam(productId, isFull.get().toBoolean())
    }
}