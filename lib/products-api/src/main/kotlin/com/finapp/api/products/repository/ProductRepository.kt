package com.finapp.api.products.repository

import com.finapp.api.products.data.Product
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductRepository {
    fun findProductById(productId: ObjectId?): Mono<Product>
    fun findAllProducts(): Flux<Product>
    fun saveProduct(product: Product): Mono<Product>
    fun deleteProduct(product: Product): Mono<DeleteResult>
}