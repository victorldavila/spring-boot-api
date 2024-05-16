package com.finapp.api.products_combination.service

import com.finapp.api.core.validation.*
import com.finapp.api.products_combination.model.ProductsCombinationArg
import com.finapp.api.products_combination.model.ProductsCombinationParam
import com.finapp.api.products_combination.model.ProductsCombinationRequest
import com.finapp.api.products_combination.model.ProductsCombinationResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface ProductsCombinationService {
    @Validated(OnRead::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getProductsCombinationById(productsCombinationParam: ProductsCombinationParam): Mono<ProductsCombinationResponse>
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getAllProductsCombination(): Flux<ProductsCombinationResponse>
    @Validated(OnUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun updateProductsCombination(productsCombinationArg: ProductsCombinationArg): Mono<ProductsCombinationResponse>
    @Validated(OnCreate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_WRITE')")
    fun createProductsCombination(productsCombinationRequest: ProductsCombinationRequest): Mono<ProductsCombinationResponse>
    @Validated(OnDelete::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_DELETE')")
    fun deleteProductsCombination(productsCombinationParam: ProductsCombinationParam?): Mono<Boolean>
}