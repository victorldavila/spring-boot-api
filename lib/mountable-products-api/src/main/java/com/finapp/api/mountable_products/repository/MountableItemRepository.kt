package com.finapp.api.mountable_products.repository

import com.finapp.api.mountable_products.data.MountableItem
import com.finapp.api.mountable_products.data.MountableStep
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MountableItemRepository {
    fun findMountableItemById(mountableItemId: ObjectId?): Mono<MountableItem>
    fun findMountableItemsByMountableStepId(mountableStepId: ObjectId?): Flux<MountableItem>
    fun saveMountableItem(mountableItem: MountableItem): Mono<MountableItem>
    fun deleteMountableItem(mountableItem: MountableItem): Mono<DeleteResult>
}