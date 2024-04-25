package com.finapp.api.mountable_products.repository

import com.finapp.api.mountable_products.data.MountableStep
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MountableProductRepository {
    fun findMountableProductById(mountableProductId: ObjectId?): Mono<MountableStep>
    fun findMountableProductByProductId(productId: ObjectId?): Flux<MountableStep>
    fun saveMountableProduct(mountableStep: MountableStep): Mono<MountableStep>
    fun deleteMountableProduct(mountableStep: MountableStep): Mono<DeleteResult>
}