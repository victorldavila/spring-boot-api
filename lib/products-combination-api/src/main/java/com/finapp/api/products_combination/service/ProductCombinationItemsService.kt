package com.finapp.api.products_combination.service

import com.finapp.api.products_combination.model.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductCombinationItemsService {
    fun getProductCombinationItemById(productCombinationItemsParam: ProductCombinationItemsParam): Mono<ProductCombinationItemResponse>
    fun getAllProductCombinationItemsByProductCombinationId(productCombinationItemsParam: ProductCombinationItemsParam): Flux<ProductCombinationItemResponse>
    fun getAllProductCombinationItems(): Flux<ProductCombinationItemResponse>
    fun updateProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse>
    fun createProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse>
    fun deleteProductCombinationItem(productCombinationItemsParam: ProductCombinationItemsParam): Mono<Boolean>
}