package com.finapp.api.mountable_products.service

import com.finapp.api.mountable_products.data.MountableStep
import com.finapp.api.mountable_products.mapper.MountableStepMapper
import com.finapp.api.mountable_products.model.*
import com.finapp.api.mountable_products.repository.MountableStepRepository
import kotlinx.coroutines.reactive.collect
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class MountableStepServiceImpl(
    private val mountableItemService: MountableItemService,
    private val mountableStepRepository: MountableStepRepository,
    private val mountableStepMapper: MountableStepMapper
): MountableStepService {
    override fun getMountableStepById(mountableStepParam: MountableStepParam): Mono<MountableStepResponse> =
        mountableStepRepository.findMountableStepById(ObjectId(mountableStepParam.mountableStepId))
            .flatMap { mountableStep ->
                mountableItemService.getMountableItemsByMountableStepId(MountableItemParam(mountableStep.id?.toHexString()))
                    .collectList()
                    .map { mountableStepMapper.mountableStepToMountableStepResponse(mountableStep).copy(items = it) }
            }

    override fun getMountableStepByProductId(mountableStepParam: MountableStepParam): Flux<MountableStepResponse> =
        mountableStepRepository.findMountableStepByProductId(ObjectId(mountableStepParam.productId))
            .flatMap { mountableStep ->
                mountableItemService.getMountableItemsByMountableStepId(MountableItemParam(mountableStep.id?.toHexString()))
                    .collectList()
                    .map { mountableStepMapper.mountableStepToMountableStepResponse(mountableStep).copy(items = it) }
            }

    override fun updateMountableStep(mountableStepArg: MountableStepArg): Mono<MountableStepResponse> =
        mountableStepRepository.findMountableStepById(ObjectId(mountableStepArg.mountableStepParam?.mountableStepId))
            .map { mountableStepMapper.mountableStepRequestToMountableStep(mountableStepArg.request!!, it) }
            .flatMap { mountableStepRepository.saveMountableStep(it) }
            .flatMap { mountableStep ->
                mountableItemService.getMountableItemsByMountableStepId(MountableItemParam(mountableStep.id?.toHexString()))
                    .collectList()
                    .map { mountableStepMapper.mountableStepToMountableStepResponse(mountableStep).copy(items = it) }
            }

    override fun createMountableStep(mountableStepArg: MountableStepArg): Mono<MountableStepResponse> =
        Mono.justOrEmpty(mountableStepArg.request)
            .map { mountableStepMapper.mountableStepRequestToMountableStep(it!!, mountableStepArg = mountableStepArg) }
            .flatMap { mountableStepRepository.saveMountableStep(it) }
            .flatMap { savedMountableStep ->
                saveMutableItem(mountableStepArg.request, savedMountableStep)
                    .map { mountableStepMapper.mountableStepToMountableStepResponse(savedMountableStep).copy(items = it) }
            }

    override fun deleteMountableStepByProductId(mountableStepParam: MountableStepParam?): Mono<Boolean> =
        mountableStepRepository.findMountableStepByProductId(ObjectId(mountableStepParam?.productId))
            .flatMap { mountableStepRepository.deleteMountableStep(it) }
            .map { it.wasAcknowledged() }
            .collectList()
            .map { it.none { wasDeleted -> !wasDeleted } }

    override fun deleteMountableStepById(mountableStepParam: MountableStepParam?): Mono<Boolean> =
        mountableStepRepository.findMountableStepById(ObjectId(mountableStepParam?.mountableStepId))
            .flatMap { mountableStepRepository.deleteMountableStep(it) }
            .map { it.wasAcknowledged() }

    private fun saveMutableItem(mountableStepRequest: MountableStepRequest?, mountableStep: MountableStep) =
        Flux.fromIterable(mountableStepRequest?.items ?: emptyList())
            .flatMap {
                mountableItemService.createMountableItem(
                    MountableItemArg(MountableItemParam(mountableStep.id?.toHexString()), it)
                )
            }.collectList()
}