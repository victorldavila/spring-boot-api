package com.finapp.api.mountable_products.repository

import com.finapp.api.mountable_products.data.MountableStep
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MountableStepRepository {
    fun findMountableStepById(mountableProductId: ObjectId?): Mono<MountableStep>
    fun findMountableStepByProductId(productId: ObjectId?): Flux<MountableStep>
    fun saveMountableStep(mountableStep: MountableStep): Mono<MountableStep>
    fun deleteMountableStep(mountableStep: MountableStep): Mono<DeleteResult>
}