package com.finapp.api.mountable_products.service

import com.finapp.api.mountable_products.mapper.MountableProductMapper
import com.finapp.api.mountable_products.model.MountableProductArg
import com.finapp.api.mountable_products.model.MountableProductParam
import com.finapp.api.mountable_products.model.MountableStepResponse
import com.finapp.api.mountable_products.repository.MountableProductRepository
import com.finapp.api.products.model.ProductParam
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class MountableProductServiceImpl(
    private val mountableProductRepository: MountableProductRepository,
    private val mountableProductMapper: MountableProductMapper
): MountableProductService {
    override fun getMountableProductByProductId(mountableProductParam: MountableProductParam): Flux<MountableStepResponse> =
        mountableProductRepository.findMountableProductByProductId(ObjectId(mountableProductParam.productId))
            .map { mountableProductMapper.mountableStepToMountableStepResponse(it) }

    override fun updateMountableProducts(mountableProductArg: MountableProductArg): Mono<MountableStepResponse> =
        mountableProductRepository.findMountableProductById(ObjectId(mountableProductArg.mountableProductParam?.mountableProductId))
            .map{ mountableProductMapper.mountableStepRequestToMountableStep(mountableProductArg.request!!, it) }
            .flatMap { mountableProductRepository.saveMountableProduct(it) }
            .map { mountableProductMapper.mountableStepToMountableStepResponse(it) }

    override fun createMountableProduct(mountableProductArg: MountableProductArg): Mono<MountableStepResponse> =
        Mono.justOrEmpty(mountableProductArg.request)
            .map {
                mountableProductMapper.mountableStepRequestToMountableStep(
                    it!!,
                    productId = ObjectId(mountableProductArg.mountableProductParam?.productId)
                )
            }
            .flatMap { mountableProductRepository.saveMountableProduct(it) }
            .map { mountableProductMapper.mountableStepToMountableStepResponse(it) }

    override fun deleteMountableProduct(mountableProductParam: MountableProductParam?): Mono<Boolean> =
        mountableProductRepository.findMountableProductByProductId(ObjectId(mountableProductParam?.productId))
            .flatMap { mountableProductRepository.deleteMountableProduct(it) }
            .map { it.wasAcknowledged() }
            .collectList()
            .map { it.none { wasDeleted -> !wasDeleted } }
}