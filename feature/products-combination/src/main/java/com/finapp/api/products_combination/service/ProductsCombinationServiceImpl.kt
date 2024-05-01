package com.finapp.api.products_combination.service

import com.finapp.api.core.error.NotFoundError
import com.finapp.api.products_combination.data.ProductsCombination
import com.finapp.api.products_combination.mapper.ProductsCombinationMapper
import com.finapp.api.products_combination.model.*
import com.finapp.api.products_combination.repository.ProductsCombinationRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class ProductsCombinationServiceImpl(
    private val productCombinationItemsService: ProductCombinationItemsService,
    private val productsCombinationRepository: ProductsCombinationRepository,
    private val productsCombinationMapper: ProductsCombinationMapper,
): ProductsCombinationService {
    override fun getProductsCombinationById(productsCombinationParam: ProductsCombinationParam): Mono<ProductsCombinationResponse> =
        findProductsCombinationById(productsCombinationParam.productCombinationId)
            .flatMap { getProductCombinationItems(it) }

    override fun getAllProductsCombination(): Flux<ProductsCombinationResponse> =
        productsCombinationRepository.findAllProductsCombination()
            .flatMap { getProductCombinationItems(it) }

    override fun updateProductsCombination(productsCombinationArg: ProductsCombinationArg): Mono<ProductsCombinationResponse> =
        findProductsCombinationById(productsCombinationArg.productsCombinationParam?.productCombinationId)
            .map { productsCombinationMapper.productsCombinationRequestToProductsCombination(productsCombinationArg.request, it) }
            .flatMap { productsCombinationRepository.saveProductCombination(it) }
            .map { productsCombinationMapper.productsCombinationToProductsCombinationResponse(it) }

    @Transactional(rollbackFor = [Exception::class])
    override fun createProductsCombination(productsCombinationRequest: ProductsCombinationRequest): Mono<ProductsCombinationResponse> =
        Mono.just(productsCombinationRequest)
            .map { productsCombinationMapper.productsCombinationRequestToProductsCombination(it) }
            .flatMap { productsCombinationRepository.saveProductCombination(it) }
            .flatMap { productCombinationSaved ->
                Flux.fromIterable(productsCombinationRequest.products ?: emptyList())
                    .flatMap {
                        productCombinationItemsService.createProductCombinationItem(
                            ProductCombinationItemsArg(ProductCombinationItemsParam(productCombinationSaved.id?.toHexString()), it)
                        )
                    }
                    .collectList()
                    .map { productsCombinationMapper.productsCombinationToProductsCombinationResponse(productCombinationSaved).copy(products = it) }
            }

    override fun deleteProductsCombination(productsCombinationParam: ProductsCombinationParam?): Mono<Boolean> =
        findProductsCombinationById(productsCombinationParam?.productCombinationId)
            .flatMap { productsCombinationRepository.deleteProductCombination(it) }
            .map { it.wasAcknowledged() }

    private fun findProductsCombinationById(productId: String?): Mono<ProductsCombination> =
        Mono.justOrEmpty(productId)
            .flatMap { productsCombinationRepository.findProductCombinationById(ObjectId(it)) }
            .switchIfEmpty { Mono.error(NotFoundError("product combination does not exists")) }

    private fun getProductCombinationItems(productsCombination: ProductsCombination) =
        productCombinationItemsService.getAllProductCombinationItemsByProductCombinationId(
            ProductCombinationItemsParam(productsCombination.id?.toHexString())
        ).collectList()
            .map { productsCombinationMapper.productsCombinationToProductsCombinationResponse(productsCombination).copy(products = it) }
}