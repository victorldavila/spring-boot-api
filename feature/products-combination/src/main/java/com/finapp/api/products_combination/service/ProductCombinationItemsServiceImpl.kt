package com.finapp.api.products_combination.service

import com.finapp.api.core.error.NotFoundError
import com.finapp.api.products.model.ProductParam
import com.finapp.api.products.service.ProductService
import com.finapp.api.products_combination.data.ProductCombinationItems
import com.finapp.api.products_combination.mapper.ProductCombinationItemsMapper
import com.finapp.api.products_combination.model.ProductCombinationItemRequest
import com.finapp.api.products_combination.model.ProductCombinationItemResponse
import com.finapp.api.products_combination.model.ProductCombinationItemsArg
import com.finapp.api.products_combination.model.ProductCombinationItemsParam
import com.finapp.api.products_combination.repository.ProductCombinationItemsRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class ProductCombinationItemsServiceImpl(
    private val productService: ProductService,
    private val productCombinationItemsRepository: ProductCombinationItemsRepository,
    private val productCombinationItemsMapper: ProductCombinationItemsMapper
): ProductCombinationItemsService {
    private fun findProductCombinationItemById(productCombinationItemId: String?): Mono<ProductCombinationItems> =
        Mono.justOrEmpty(productCombinationItemId)
            .flatMap { productCombinationItemsRepository.findProductCombinationItemById(ObjectId(it)) }
            .switchIfEmpty { Mono.error(NotFoundError("product combination does not exists")) }

    override fun getProductCombinationItemById(productCombinationItemsParam: ProductCombinationItemsParam): Mono<ProductCombinationItemResponse> =
        findProductCombinationItemById(productCombinationItemsParam.productCombinationItemId)
            .flatMap { productCombinationItem ->
                productService.getProductById(ProductParam(productCombinationItem.productId?.toHexString()))
                    .map { productCombinationItemsMapper.productCombinationItemToProductCombinationItemResponse(productCombinationItem).copy(product = it) }
            }


    override fun getAllProductCombinationItemsByProductCombinationId(productCombinationItemsParam: ProductCombinationItemsParam): Flux<ProductCombinationItemResponse> =
        productCombinationItemsRepository.findProductCombinationItemsByProductCombinationId(ObjectId(productCombinationItemsParam.productCombinationId))
            .flatMap { productCombinationItem ->
                productService.getProductById(ProductParam(productCombinationItem.productId?.toHexString()))
                    .map { productCombinationItemsMapper.productCombinationItemToProductCombinationItemResponse(productCombinationItem).copy(product = it) }
            }

    override fun getAllProductCombinationItems(): Flux<ProductCombinationItemResponse> =
        productCombinationItemsRepository.findAllProductCombinationItems()
            .flatMap { productCombinationItem ->
                productService.getProductById(ProductParam(productCombinationItem.productId?.toHexString()))
                    .map { productCombinationItemsMapper.productCombinationItemToProductCombinationItemResponse(productCombinationItem).copy(product = it) }
            }

    override fun updateProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse> =
        findProductCombinationItemById(productCombinationItemsArg.productsCombinationParam?.productCombinationItemId)
            .map { productCombinationItemsMapper.productCombinationItemRequestToProductCombinationItem(productCombinationItemsArg.request, it) }
            .flatMap { productCombinationItemsRepository.saveProductCombinationItem(it) }
            .map { productCombinationItemsMapper.productCombinationItemToProductCombinationItemResponse(it) }

    override fun createProductCombinationItem(productCombinationItemsArg: ProductCombinationItemsArg): Mono<ProductCombinationItemResponse> =
        Mono.just(productCombinationItemsArg)
            .map { productCombinationItemsMapper.productCombinationItemRequestToProductCombinationItem(it.request, productCombinationItemsParam = productCombinationItemsArg.productsCombinationParam) }
            .flatMap { productCombinationItemsRepository.saveProductCombinationItem(it) }
            .map { productCombinationItemsMapper.productCombinationItemToProductCombinationItemResponse(it) }

    override fun deleteProductCombinationItem(productCombinationItemsParam: ProductCombinationItemsParam): Mono<Boolean> =
        findProductCombinationItemById(productCombinationItemsParam.productCombinationItemId)
            .flatMap { productCombinationItemsRepository.deleteProductCombinationItem(it) }
            .map { it.wasAcknowledged() }
}