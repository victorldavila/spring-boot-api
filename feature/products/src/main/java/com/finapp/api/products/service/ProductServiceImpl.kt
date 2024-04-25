package com.finapp.api.products.service

import com.finapp.api.core.error.NotFoundError
import com.finapp.api.mountable_products.model.MountableProductArg
import com.finapp.api.mountable_products.model.MountableProductParam
import com.finapp.api.mountable_products.service.MountableProductService
import com.finapp.api.products.data.Product
import com.finapp.api.products.mapper.ProductMapper
import com.finapp.api.products.model.ProductArg
import com.finapp.api.products.model.ProductParam
import com.finapp.api.products.model.ProductRequest
import com.finapp.api.products.model.ProductResponse
import com.finapp.api.products.repository.ProductRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class ProductServiceImpl(
    private val mountableProductService: MountableProductService,
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper,
): ProductService {
    override fun getProductById(productParam: ProductParam): Mono<ProductResponse> =
        findProductById(productParam.productId)
            .flatMap { product ->
                mountableProductService.getMountableProductByProductId(MountableProductParam(product.id?.toHexString()))
                    .collectList()
                    .map { productMapper.productToProductResponse(product).copy(steps = it) }
            }

    override fun getAllProducts(): Flux<ProductResponse> =
        productRepository.findAllProducts()
            .flatMap { product ->
                mountableProductService.getMountableProductByProductId(MountableProductParam(product.id?.toHexString()))
                    .collectList()
                    .map { productMapper.productToProductResponse(product).copy(steps = it) }
            }

    override fun updateProduct(productArg: ProductArg): Mono<ProductResponse> =
        findProductById(productArg.productParam?.productId)
            .map { productMapper.productRequestToProduct(productArg.request, it) }
            .flatMap { productRepository.saveProduct(it) }
            .flatMap { savedProduct ->
                mountableProductService.getMountableProductByProductId(MountableProductParam(savedProduct.id?.toHexString()))
                    .collectList()
                    .map { productMapper.productToProductResponse(savedProduct).copy(steps = it) }
            }

    override fun createProduct(productRequest: ProductRequest): Mono<ProductResponse> =
        Mono.just(productRequest)
            .map { productMapper.productRequestToProduct(it) }
            .flatMap { product ->
                productRepository.saveProduct(product)
                    .flatMap { savedProduct ->
                        Flux.fromIterable(productRequest.steps ?: emptyList())
                            .flatMap {
                                mountableProductService.createMountableProduct(
                                    MountableProductArg(
                                        MountableProductParam(savedProduct.id?.toHexString()),
                                        it
                                    )
                                )
                            }
                            .collectList()
                            .map { productMapper.productToProductResponse(savedProduct).copy(steps = it) }
                    }
            }

    override fun deleteProduct(productParam: ProductParam?): Mono<Boolean> =
        findProductById(productParam?.productId)
            .flatMap { productRepository.deleteProduct(it) }
            .map { it.wasAcknowledged() }

    private fun findProductById(productId: String?): Mono<Product> =
        Mono.justOrEmpty(productId)
            .flatMap { productRepository.findProductById(ObjectId(it)) }
            .switchIfEmpty { Mono.error(NotFoundError("product does not exists")) }
}