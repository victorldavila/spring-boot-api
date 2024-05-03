package com.finapp.api.mountable_products.service

import com.finapp.api.core.validation.*
import com.finapp.api.mountable_products.model.*
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface MountableItemService {
    @Validated(OnRead::class)
    fun getMountableItemsById(@Valid mountableItemParam: MountableItemParam): Mono<MountableItemResponse>
    @Validated(OnReadItems::class)
    fun getMountableItemsByMountableStepId(@Valid mountableItemParam: MountableItemParam): Flux<MountableItemResponse>
    @Validated(OnUpdate::class)
    fun completeUpdateMountableItem(@Valid mountableItemArg: MountableItemArg): Mono<MountableItemResponse>
    @Validated(OnPartialUpdate::class)
    fun partialUpdateMountableItem(@Valid mountableItemArg: MountableItemArg): Mono<MountableItemResponse>
    @Validated(OnCreate::class)
    fun createMountableItem(@Valid mountableItemArg: MountableItemArg): Mono<MountableItemResponse>
    @Validated(OnDelete::class)
    fun deleteMountableItemById(@Valid mountableItemParam: MountableItemParam?): Mono<Boolean>
    @Validated(OnDeleteItems::class)
    fun deleteMountableItemByMountableStepId(@Valid mountableItemParam: MountableItemParam?): Mono<Boolean>
}