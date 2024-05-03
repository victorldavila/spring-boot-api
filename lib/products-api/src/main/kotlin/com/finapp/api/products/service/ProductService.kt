package com.finapp.api.products.service

import com.finapp.api.core.validation.*
import com.finapp.api.products.model.ProductArg
import com.finapp.api.products.model.ProductParam
import com.finapp.api.products.model.ProductRequest
import com.finapp.api.products.model.ProductResponse
import jakarta.validation.Valid
import org.springframework.http.codec.multipart.Part
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface ProductService {
    @Validated(OnRead::class)
    fun getProductById(@Valid productParam: ProductParam): Mono<ProductResponse>
    fun getAllProducts(): Flux<ProductResponse>
    @Validated(OnUpdate::class)
    fun completeUpdateProduct(@Valid productArg: ProductArg): Mono<ProductResponse>
    @Validated(OnPartialUpdate::class)
    fun partialUpdateProduct(@Valid productArg: ProductArg): Mono<ProductResponse>
    @Validated(OnCreate::class)
    fun createProduct(@Valid productRequest: ProductRequest): Mono<ProductResponse>
    @Validated(OnDelete::class)
    fun deleteProduct(@Valid productParam: ProductParam?): Mono<Boolean>
    fun updateProductImage(get: List<Part>?, productParam: ProductParam): Mono<ProductResponse>
}