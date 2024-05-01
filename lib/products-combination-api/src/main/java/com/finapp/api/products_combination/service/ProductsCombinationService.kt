package com.finapp.api.products_combination.service

import com.finapp.api.products_combination.model.ProductsCombinationArg
import com.finapp.api.products_combination.model.ProductsCombinationParam
import com.finapp.api.products_combination.model.ProductsCombinationRequest
import com.finapp.api.products_combination.model.ProductsCombinationResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductsCombinationService {
    fun getProductsCombinationById(productsCombinationParam: ProductsCombinationParam): Mono<ProductsCombinationResponse>
    fun getAllProductsCombination(): Flux<ProductsCombinationResponse>
    fun updateProductsCombination(productsCombinationArg: ProductsCombinationArg): Mono<ProductsCombinationResponse>
    fun createProductsCombination(productsCombinationRequest: ProductsCombinationRequest): Mono<ProductsCombinationResponse>
    fun deleteProductsCombination(productsCombinationParam: ProductsCombinationParam?): Mono<Boolean>
}