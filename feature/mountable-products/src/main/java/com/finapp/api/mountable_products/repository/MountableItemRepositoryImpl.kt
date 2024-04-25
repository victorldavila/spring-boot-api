package com.finapp.api.mountable_products.repository

import com.finapp.api.mountable_products.data.MountableItem
import com.finapp.api.mountable_products.data.MountableStep
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class MountableItemRepositoryImpl(
    private val template: ReactiveMongoTemplate
): MountableItemRepository {
    override fun findMountableItemById(mountableItemId: ObjectId?): Mono<MountableItem> =
        template.findById(mountableItemId, MountableItem::class.java)

    override fun findMountableItemsByMountableStepId(mountableStepId: ObjectId?): Flux<MountableItem> =
        template.find(getMountableProductQueryByField(MountableItem.MOUNTABLE_STEP_ID_FIELD, mountableStepId), MountableItem::class.java)

    override fun saveMountableItem(mountableItem: MountableItem): Mono<MountableItem> =
        template.save(mountableItem)

    override fun deleteMountableItem(mountableItem: MountableItem): Mono<DeleteResult> =
        template.remove(mountableItem)

    private fun <T>getMountableProductQueryByField(field: String, value: T): Query =
        Query(
            Criteria.where(field)
                .isEqualTo(value)
        )
}