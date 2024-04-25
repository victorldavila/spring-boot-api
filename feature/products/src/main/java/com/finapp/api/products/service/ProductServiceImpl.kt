package com.finapp.api.products.service

import com.finapp.api.core.error.NotFoundError
import com.finapp.api.mountable_products.model.MountableStepArg
import com.finapp.api.mountable_products.model.MountableStepParam
import com.finapp.api.mountable_products.model.MountableStepRequest
import com.finapp.api.mountable_products.service.MountableStepService
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
    private val mountableStepService: MountableStepService,
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper,
): ProductService {
    override fun getProductById(productParam: ProductParam): Mono<ProductResponse> =
        findProductById(productParam.productId)
            .flatMap { product ->
                mountableStepService.getMountableStepByProductId(getMountableParam(product))
                    .collectList()
                    .map { productMapper.productToProductResponse(product).copy(steps = it) }
            }

    override fun getAllProducts(): Flux<ProductResponse> =
        productRepository.findAllProducts()
            .flatMap { product -> getMountableSteps(product) }

    override fun updateProduct(productArg: ProductArg): Mono<ProductResponse> =
        findProductById(productArg.productParam?.productId)
            .map { productMapper.productRequestToProduct(productArg.request, it) }
            .flatMap { productRepository.saveProduct(it) }
            .flatMap { savedProduct -> getMountableSteps(savedProduct) }

    override fun createProduct(productRequest: ProductRequest): Mono<ProductResponse> =
        Mono.just(productRequest)
            .map { productMapper.productRequestToProduct(it) }
            .flatMap { product ->
                productRepository.saveProduct(product)
                    .flatMap { savedProduct -> createMountableStep(productRequest, savedProduct) }
            }

    override fun deleteProduct(productParam: ProductParam?): Mono<Boolean> =
        findProductById(productParam?.productId)
            .flatMap { productRepository.deleteProduct(it) }
            .map { it.wasAcknowledged() }

    private fun createMountableStep(productRequest: ProductRequest, savedProduct: Product) =
        Flux.fromIterable(productRequest.steps ?: emptyList())
            .flatMap { mountableStepService.createMountableStep(getMountableArg(savedProduct, it)) }
            .collectList()
            .map { productMapper.productToProductResponse(savedProduct).copy(steps = it) }

    private fun getMountableSteps(savedProduct: Product) =
        mountableStepService.getMountableStepByProductId(getMountableParam(savedProduct))
            .collectList()
            .map { productMapper.productToProductResponse(savedProduct).copy(steps = it) }

    private fun findProductById(productId: String?): Mono<Product> =
        Mono.justOrEmpty(productId)
            .flatMap { productRepository.findProductById(ObjectId(it)) }
            .switchIfEmpty { Mono.error(NotFoundError("product does not exists")) }

    private fun getMountableParam(product: Product, mountableStepId: String? = null) =
        MountableStepParam(product.id?.toHexString(), mountableStepId)

    private fun getMountableArg(product: Product, mountableStepRequest: MountableStepRequest? = null) =
        MountableStepArg(
            MountableStepParam(product.id?.toHexString(), mountableStepRequest?.id),
            mountableStepRequest
        )
}