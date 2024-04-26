package com.finapp.api.mountable_products.service

import com.finapp.api.core.validation.*
import com.finapp.api.mountable_products.model.MountableStepArg
import com.finapp.api.mountable_products.model.MountableStepParam
import com.finapp.api.mountable_products.model.MountableStepResponse
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface MountableStepService {
    @Validated(OnRead::class)
    fun getMountableStepById(@Valid mountableStepParam: MountableStepParam): Mono<MountableStepResponse>
    @Validated(OnRead::class)
    fun getMountableStepByProductId(@Valid mountableStepParam: MountableStepParam): Flux<MountableStepResponse>
    @Validated(OnUpdate::class)
    fun updateMountableStep(@Valid mountableStepArg: MountableStepArg): Mono<MountableStepResponse>
    @Validated(OnCreate::class)
    fun createMountableStep(@Valid mountableStepArg: MountableStepArg): Mono<MountableStepResponse>
    @Validated(OnDeleteItems::class)
    fun deleteMountableStepByProductId(@Valid mountableStepParam: MountableStepParam?): Mono<Boolean>
    @Validated(OnDelete::class)
    fun deleteMountableStepById(mountableStepParam: MountableStepParam?): Mono<Boolean>
}