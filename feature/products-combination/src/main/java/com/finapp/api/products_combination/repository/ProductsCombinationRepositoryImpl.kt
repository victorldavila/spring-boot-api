package com.finapp.api.products_combination.repository

import com.finapp.api.products_combination.data.ProductsCombination
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class ProductsCombinationRepositoryImpl(
    private val template: ReactiveMongoTemplate
): ProductsCombinationRepository {
    override fun findProductCombinationById(productCombinationId: ObjectId?): Mono<ProductsCombination> =
        template.findById(productCombinationId, ProductsCombination::class.java)

    override fun findAllProductsCombination(): Flux<ProductsCombination> =
        template.findAll(ProductsCombination::class.java)

    override fun saveProductCombination(productCombination: ProductsCombination): Mono<ProductsCombination> =
        template.save(productCombination)

    override fun deleteProductCombination(productCombination: ProductsCombination): Mono<DeleteResult> =
        template.remove(productCombination)
}