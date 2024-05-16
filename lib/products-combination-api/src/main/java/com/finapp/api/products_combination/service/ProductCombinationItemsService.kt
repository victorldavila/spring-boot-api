package com.finapp.api.products_combination.service

import com.finapp.api.core.validation.*
import com.finapp.api.products_combination.model.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductCombinationItemsService {
    @Validated(OnRead::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getProductCombinationItemById(productCombinationItemsParam: ProductCombinationItemsParam): Mono<ProductCombinationItemResponse>
    @Validated(OnReadItems::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getAllProductCombinationItemsByProductCombinationId(productCombinationItemsParam: ProductCombinationItemsParam): Flux<ProductCombinationItemResponse>
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getAllProductCombinationItems(): Flux<ProductCombinationItemResponse>
    @Validated(OnUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun completeUpdateProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse>
    @Validated(OnPartialUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun partialUpdateProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse>
    @Validated(OnCreate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_WRITE')")
    fun createProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse>
    @Validated(OnDelete::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_DELETE')")
    fun deleteProductCombinationItem(productCombinationItemsParam: ProductCombinationItemsParam): Mono<Boolean>
}