package com.finapp.api.mountable_products.service

import com.finapp.api.core.validation.*
import com.finapp.api.mountable_products.model.*
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface MountableItemService {
    @Validated(OnRead::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getMountableItemsById(@Valid mountableItemParam: MountableItemParam): Mono<MountableItemResponse>
    @Validated(OnReadItems::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getMountableItemsByMountableStepId(@Valid mountableItemParam: MountableItemParam): Flux<MountableItemResponse>
    @Validated(OnUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun completeUpdateMountableItem(@Valid mountableItemArg: MountableItemArg): Mono<MountableItemResponse>
    @Validated(OnPartialUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun partialUpdateMountableItem(@Valid mountableItemArg: MountableItemArg): Mono<MountableItemResponse>
    @Validated(OnCreate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_WRITE')")
    fun createMountableItem(@Valid mountableItemArg: MountableItemArg): Mono<MountableItemResponse>
    @Validated(OnDelete::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_DELETE')")
    fun deleteMountableItemById(@Valid mountableItemParam: MountableItemParam?): Mono<Boolean>
    @Validated(OnDeleteItems::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_DELETE')")
    fun deleteMountableItemByMountableStepId(@Valid mountableItemParam: MountableItemParam?): Mono<Boolean>
}