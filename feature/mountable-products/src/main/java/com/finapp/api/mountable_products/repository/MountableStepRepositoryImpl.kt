package com.finapp.api.mountable_products.repository

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
class MountableStepRepositoryImpl(
    private val template: ReactiveMongoTemplate
): MountableStepRepository {
    override fun findMountableStepById(mountableProductId: ObjectId?): Mono<MountableStep> =
        template.findById(mountableProductId, MountableStep::class.java)

    override fun findMountableStepByProductId(productId: ObjectId?): Flux<MountableStep> =
        template.find(getMountableProductQueryByField(MountableStep.PRODUCT_ID_FIELD, productId), MountableStep::class.java)

    override fun saveMountableStep(mountableStep: MountableStep): Mono<MountableStep> =
        template.save(mountableStep)

    override fun deleteMountableStep(mountableStep: MountableStep): Mono<DeleteResult> =
        template.remove(mountableStep)

    private fun <T>getMountableProductQueryByField(field: String, value: T): Query =
        Query(
            Criteria.where(field)
                .isEqualTo(value)
        )
}