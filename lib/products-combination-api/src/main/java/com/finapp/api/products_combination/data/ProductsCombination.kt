package com.finapp.api.products_combination.data

import com.finapp.api.products_combination.model.ProductsCombinationType
import com.finapp.api.products_combination.data.ProductsCombination.Companion.COLLECTION_NAME
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = COLLECTION_NAME)
data class ProductsCombination(
    @MongoId val id: ObjectId? = null,
    @Field(NAME_FIELD) val name: String,
    @Field(PRICE_FIELD) val price: BigDecimal?,
    @Field(ACTIVE_FIELD) val isActive: Boolean,
    @Field(TYPE_FIELD) val type: ProductsCombinationType,
    @Field(CATEGORY_FIELD) val category: String,

    @Field(PRODUCTS_FIELD) val products: List<ProductCombinationItems>? = null,

    @CreatedDate @Field(CREATED_DATE_FIELD) val createdDate: LocalDateTime? = null,
    @LastModifiedDate @Field(LAST_MODIFIED_DATE_FIELD) var lastModifiedDate: LocalDateTime? = null,
    @LastModifiedBy @Field(LAST_MODIFIED_BY_FIELD) var lastModifiedBy: ObjectId? = null,
    @CreatedBy @Field(CREATED_BY_FIELD) var createdBy: ObjectId? = null
): Serializable {
    companion object {
        const val COLLECTION_NAME = "products_combination"

        const val NAME_FIELD = "name"
        const val PRICE_FIELD = "price"
        const val ACTIVE_FIELD = "is_active"
        const val PRODUCTS_FIELD = "products"
        const val TYPE_FIELD = "type"
        const val CATEGORY_FIELD = "category"

        const val CREATED_DATE_FIELD = "created_date"
        const val LAST_MODIFIED_DATE_FIELD = "last_modified_date"
        const val LAST_MODIFIED_BY_FIELD = "last_modified_by"
        const val CREATED_BY_FIELD = "created_by"
    }
}
