package com.finapp.api.mountable_products.data.listener

import com.finapp.api.mountable_products.data.MountableStep
import com.finapp.api.mountable_products.repository.MountableItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent
import org.springframework.stereotype.Component

@Component
class MountableStepCascadeDeleteMongoEventListener: AbstractMongoEventListener<MountableStep>() {

    @Autowired
    private lateinit var mountableItemRepository: MountableItemRepository

    override fun onBeforeDelete(event: BeforeDeleteEvent<MountableStep>) {
        val mountableStepId = event.source.getObjectId("_id")

        mountableItemRepository.findMountableItemsByMountableStepId(mountableStepId)
            .flatMap { mountableItemRepository.deleteMountableItem(it) }
            .subscribe()
    }
}