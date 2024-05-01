package com.finapp.api.products_combination.repository

import com.finapp.api.products_combination.data.ProductCombinationItems
import com.finapp.api.products_combination.data.ProductsCombination
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductCombinationItemsRepository {
    fun findProductCombinationItemById(productCombinationItemId: ObjectId?): Mono<ProductCombinationItems>
    fun findProductCombinationItemsByProductCombinationId(productCombinationId: ObjectId?): Flux<ProductCombinationItems>
    fun findAllProductCombinationItems(): Flux<ProductCombinationItems>
    fun saveProductCombinationItem(productCombinationItems: ProductCombinationItems): Mono<ProductCombinationItems>
    fun deleteProductCombinationItem(productCombinationItems: ProductCombinationItems): Mono<DeleteResult>
}