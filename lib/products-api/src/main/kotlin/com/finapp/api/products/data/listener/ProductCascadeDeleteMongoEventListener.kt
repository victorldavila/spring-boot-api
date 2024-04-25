package com.finapp.api.products.data.listener

import com.finapp.api.mountable_products.repository.MountableStepRepository
import com.finapp.api.products.data.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent
import org.springframework.stereotype.Component

@Component
class ProductCascadeDeleteMongoEventListener: AbstractMongoEventListener<Product>() {

    @Autowired
    private lateinit var mountableStepRepository: MountableStepRepository

    override fun onBeforeDelete(event: BeforeDeleteEvent<Product>) {
        val productId = event.source.getObjectId("_id")

        mountableStepRepository.findMountableStepByProductId(productId)
            .flatMap { mountableStepRepository.deleteMountableStep(it) }
            .subscribe()
    }
}