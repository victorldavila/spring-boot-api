package com.finapp.api.products

import com.finapp.api.core.error.BaseHandler
import com.finapp.api.core.error.ValidationHandler
import com.finapp.api.products.model.ProductArg
import com.finapp.api.products.model.ProductParam
import com.finapp.api.products.model.ProductRequest
import com.finapp.api.products.model.ProductResponse
import com.finapp.api.products.service.ProductService
import com.finapp.api.products.service.ProductServiceImpl
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

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

    fun getAllProducts(serverRequest: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .body(productsService.getAllProducts(), ProductResponse::class.java)

    fun updateProduct(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { serverRequest ->
                serverRequest.bodyToMono(ProductRequest::class.java)
                    .map { ProductArg(getProductParam(serverRequest), it) }
            }
            .flatMap { productsService.updateProduct(it) }
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
        val userId = serverRequest.pathVariable("productId")

        return ProductParam(userId)
    }
}