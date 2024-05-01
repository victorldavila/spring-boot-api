package com.finapp.api.products_combination.data.listener

import com.finapp.api.products_combination.data.ProductsCombination
import com.finapp.api.products_combination.repository.ProductCombinationItemsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent
import org.springframework.stereotype.Component

@Component
class ProductsCombinationCascadeDeleteMongoEventListener: AbstractMongoEventListener<ProductsCombination>() {

    @Autowired
    private lateinit var productCombinationItemsRepository: ProductCombinationItemsRepository

    override fun onBeforeDelete(event: BeforeDeleteEvent<ProductsCombination>) {
        val productCombinationId = event.source.getObjectId("_id")

        productCombinationItemsRepository.findProductCombinationItemsByProductCombinationId(productCombinationId)
            .flatMap { productCombinationItemsRepository.deleteProductCombinationItem(it) }
            .subscribe()
    }
}