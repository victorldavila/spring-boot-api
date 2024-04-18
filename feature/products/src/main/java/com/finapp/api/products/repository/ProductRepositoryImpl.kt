package com.finapp.api.products.repository

import com.finapp.api.products.data.Product
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class ProductRepositoryImpl(
    private val template: ReactiveMongoTemplate
): ProductRepository {
    override fun findProductById(productId: ObjectId?): Mono<Product> =
        template.findById(productId, Product::class.java)

    override fun findAllProducts(): Flux<Product> =
        template.findAll(Product::class.java)

    override fun saveProduct(product: Product): Mono<Product> =
        template.save(product)

    override fun deleteProduct(product: Product): Mono<DeleteResult> =
        template.remove(product)

    private fun <T>getUserQueryByField(field: String, value: T): Query =
        Query(
            Criteria.where(field)
                .isEqualTo(value)
        )
}