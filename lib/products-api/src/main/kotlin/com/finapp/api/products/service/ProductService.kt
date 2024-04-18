package com.finapp.api.products.service

import com.finapp.api.products.model.ProductArg
import com.finapp.api.products.model.ProductParam
import com.finapp.api.products.model.ProductRequest
import com.finapp.api.products.model.ProductResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductService {
    fun getProductById(productParam: ProductParam): Mono<ProductResponse>
    fun getAllProducts(): Flux<ProductResponse>
    fun updateProduct(productArg: ProductArg): Mono<ProductResponse>
    fun createProduct(productRequest: ProductRequest): Mono<ProductResponse>
    fun deleteProduct(productParam: ProductParam?): Mono<Boolean>
}