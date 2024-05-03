package com.finapp.api.products_combination.service

import com.finapp.api.core.validation.*
import com.finapp.api.products_combination.model.*
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductCombinationItemsService {
    @Validated(OnRead::class)
    fun getProductCombinationItemById(productCombinationItemsParam: ProductCombinationItemsParam): Mono<ProductCombinationItemResponse>
    @Validated(OnReadItems::class)
    fun getAllProductCombinationItemsByProductCombinationId(productCombinationItemsParam: ProductCombinationItemsParam): Flux<ProductCombinationItemResponse>
    fun getAllProductCombinationItems(): Flux<ProductCombinationItemResponse>
    @Validated(OnUpdate::class)
    fun completeUpdateProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse>
    @Validated(OnPartialUpdate::class)
    fun partialUpdateProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse>
    @Validated(OnCreate::class)
    fun createProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse>
    @Validated(OnDelete::class)
    fun deleteProductCombinationItem(productCombinationItemsParam: ProductCombinationItemsParam): Mono<Boolean>
}