package com.finapp.api.products_combination.repository

import com.finapp.api.products_combination.data.ProductsCombination
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductsCombinationRepository {
    fun findProductCombinationById(productCombinationId: ObjectId?): Mono<ProductsCombination>
    fun findAllProductsCombination(): Flux<ProductsCombination>
    fun saveProductCombination(productCombination: ProductsCombination): Mono<ProductsCombination>
    fun deleteProductCombination(productCombination: ProductsCombination): Mono<DeleteResult>
}