package com.finapp.api.products_combination.repository

import com.finapp.api.products_combination.data.ProductCombinationItems
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
class ProductCombinationItemsRepositoryImpl(
    private val template: ReactiveMongoTemplate
): ProductCombinationItemsRepository {
    override fun findProductCombinationItemById(productCombinationItemId: ObjectId?): Mono<ProductCombinationItems> =
        template.findById(productCombinationItemId, ProductCombinationItems::class.java)

    override fun findProductCombinationItemsByProductCombinationId(productCombinationId: ObjectId?): Flux<ProductCombinationItems> =
        template.find(getProductCombinationItemsQueryByField(ProductCombinationItems.PRODUCTS_COMBINATION_ID_FIELD, productCombinationId), ProductCombinationItems::class.java)

    override fun findAllProductCombinationItems(): Flux<ProductCombinationItems> =
        template.findAll(ProductCombinationItems::class.java)

    override fun saveProductCombinationItem(productCombinationItems: ProductCombinationItems): Mono<ProductCombinationItems> =
        template.save(productCombinationItems)

    override fun deleteProductCombinationItem(productCombinationItems: ProductCombinationItems): Mono<DeleteResult> =
        template.remove(productCombinationItems)

    private fun <T>getProductCombinationItemsQueryByField(field: String, value: T): Query =
        Query(
            Criteria.where(field)
                .isEqualTo(value)
        )
}