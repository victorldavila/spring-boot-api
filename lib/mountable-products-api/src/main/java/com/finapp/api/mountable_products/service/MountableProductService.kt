package com.finapp.api.mountable_products.service

import com.finapp.api.core.validation.OnCreate
import com.finapp.api.core.validation.OnDelete
import com.finapp.api.core.validation.OnRead
import com.finapp.api.core.validation.OnUpdate
import com.finapp.api.mountable_products.model.MountableProductArg
import com.finapp.api.mountable_products.model.MountableProductParam
import com.finapp.api.mountable_products.model.MountableStepResponse
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface MountableProductService {
    @Validated(OnRead::class)
    fun getMountableProductByProductId(@Valid mountableProductParam: MountableProductParam): Flux<MountableStepResponse>
    @Validated(OnUpdate::class)
    fun updateMountableProducts(@Valid mountableProductArg: MountableProductArg): Mono<MountableStepResponse>
    @Validated(OnCreate::class)
    fun createMountableProduct(@Valid mountableProductArg: MountableProductArg): Mono<MountableStepResponse>
    @Validated(OnDelete::class)
    fun deleteMountableProduct(@Valid mountableProductParam: MountableProductParam?): Mono<Boolean>
}