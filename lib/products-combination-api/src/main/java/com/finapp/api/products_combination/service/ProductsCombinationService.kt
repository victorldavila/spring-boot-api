package com.finapp.api.products_combination.service

import com.finapp.api.core.validation.*
import com.finapp.api.products_combination.model.ProductsCombinationArg
import com.finapp.api.products_combination.model.ProductsCombinationParam
import com.finapp.api.products_combination.model.ProductsCombinationRequest
import com.finapp.api.products_combination.model.ProductsCombinationResponse
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface ProductsCombinationService {
    @Validated(OnRead::class)
    fun getProductsCombinationById(productsCombinationParam: ProductsCombinationParam): Mono<ProductsCombinationResponse>
    fun getAllProductsCombination(): Flux<ProductsCombinationResponse>
    @Validated(OnUpdate::class)
    fun updateProductsCombination(productsCombinationArg: ProductsCombinationArg): Mono<ProductsCombinationResponse>
    @Validated(OnCreate::class)
    fun createProductsCombination(productsCombinationRequest: ProductsCombinationRequest): Mono<ProductsCombinationResponse>
    @Validated(OnDelete::class)
    fun deleteProductsCombination(productsCombinationParam: ProductsCombinationParam?): Mono<Boolean>
}