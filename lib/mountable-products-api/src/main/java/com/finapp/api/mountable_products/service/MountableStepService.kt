package com.finapp.api.mountable_products.service

import com.finapp.api.core.validation.*
import com.finapp.api.mountable_products.model.MountableStepArg
import com.finapp.api.mountable_products.model.MountableStepParam
import com.finapp.api.mountable_products.model.MountableStepResponse
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface MountableStepService {
    @Validated(OnRead::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getMountableStepById(@Valid mountableStepParam: MountableStepParam): Mono<MountableStepResponse>
    @Validated(OnReadItems::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getMountableStepByProductId(@Valid mountableStepParam: MountableStepParam): Flux<MountableStepResponse>
    @Validated(OnUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun completeUpdateMountableStep(@Valid mountableStepArg: MountableStepArg): Mono<MountableStepResponse>
    @Validated(OnPartialUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun partialUpdateMountableStep(@Valid mountableStepArg: MountableStepArg): Mono<MountableStepResponse>
    @Validated(OnCreate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_WRITE')")
    fun createMountableStep(@Valid mountableStepArg: MountableStepArg): Mono<MountableStepResponse>
    @Validated(OnDeleteItems::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_DELETE')")
    fun deleteMountableStepByProductId(@Valid mountableStepParam: MountableStepParam?): Mono<Boolean>
    @Validated(OnDelete::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_DELETE')")
    fun deleteMountableStepById(mountableStepParam: MountableStepParam?): Mono<Boolean>
}