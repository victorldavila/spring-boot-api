package com.finapp.api.mountable_products.service

import com.finapp.api.mountable_products.mapper.MountableItemMapper
import com.finapp.api.mountable_products.model.MountableItemArg
import com.finapp.api.mountable_products.model.MountableItemParam
import com.finapp.api.mountable_products.model.MountableItemResponse
import com.finapp.api.mountable_products.repository.MountableItemRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class MountableItemServiceImpl(
    private val mountableItemRepository: MountableItemRepository,
    private val mountableItemMapper: MountableItemMapper
): MountableItemService {
    override fun getMountableItemsById(mountableItemParam: MountableItemParam): Mono<MountableItemResponse> =
        mountableItemRepository.findMountableItemById(ObjectId(mountableItemParam.mountableItemId))
            .map { mountableItemMapper.mountableItemToMountableItemResponse(it) }

    override fun getMountableItemsByMountableStepId(mountableItemParam: MountableItemParam): Flux<MountableItemResponse> =
        mountableItemRepository.findMountableItemsByMountableStepId(ObjectId(mountableItemParam.mountableStepId))
            .map { mountableItemMapper.mountableItemToMountableItemResponse(it) }

    override fun updateMountableItem(mountableItemArg: MountableItemArg): Mono<MountableItemResponse> =
        mountableItemRepository.findMountableItemById(ObjectId(mountableItemArg.mountableItemParam?.mountableItemId))
            .map{ mountableItemMapper.mountableItemRequestToMountableItem(mountableItemArg.request!!, it) }
            .flatMap { mountableItemRepository.saveMountableItem(it) }
            .map { mountableItemMapper.mountableItemToMountableItemResponse(it) }

    override fun createMountableItem(mountableItemArg: MountableItemArg): Mono<MountableItemResponse> =
        Mono.justOrEmpty(mountableItemArg.request)
            .map { mountableItemMapper.mountableItemRequestToMountableItem(it!!, mountableItemArg.mountableItemParam) }
            .flatMap { mountableItemRepository.saveMountableItem(it) }
            .map { mountableItemMapper.mountableItemToMountableItemResponse(it) }

    override fun deleteMountableItemById(mountableItemParam: MountableItemParam?): Mono<Boolean> =
        mountableItemRepository.findMountableItemById(ObjectId(mountableItemParam?.mountableItemId))
            .flatMap { mountableItemRepository.deleteMountableItem(it) }
            .map { it.wasAcknowledged() }

    override fun deleteMountableItemByMountableStepId(mountableItemParam: MountableItemParam?): Mono<Boolean> =
        mountableItemRepository.findMountableItemsByMountableStepId(ObjectId(mountableItemParam?.mountableStepId))
            .flatMap { mountableItemRepository.deleteMountableItem(it) }
            .map { it.wasAcknowledged() }
            .collectList()
            .map { it.none { wasDeleted -> !wasDeleted } }
}