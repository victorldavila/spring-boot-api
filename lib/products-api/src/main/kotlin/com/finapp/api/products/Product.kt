package com.finapp.api.products

import com.finapp.api.products.Product.Companion.COLLECTION_NAME
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = COLLECTION_NAME)
data class Product(
    @MongoId val id: ObjectId? = null,
    @Field(NAME_FIELD) val name: String,
    @Field(VALUE_FIELD) val value: BigDecimal,
    @Field(ACTIVE_FIELD) val isActive: Boolean,
    @Field(VARIABLE_FIELD) val isVariable: Boolean,
    @Field(TYPE_FIELD) val type: String,
    @Field(CATEGORY_FIELD) val category: String,
    @CreatedDate val createdDate: LocalDateTime? = null,
    @LastModifiedDate val lastModifiedDate: LocalDateTime? = null
): Serializable {
    companion object {
        const val COLLECTION_NAME = "products"

        const val NAME_FIELD = "name"
        const val VALUE_FIELD = "value"
        const val ACTIVE_FIELD = "is_active"
        const val VARIABLE_FIELD = "is_variable"
        const val TYPE_FIELD = "type"
        const val CATEGORY_FIELD = "category"
    }
}
