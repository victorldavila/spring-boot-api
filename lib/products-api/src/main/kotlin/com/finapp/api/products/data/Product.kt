package com.finapp.api.products.data

import com.finapp.api.products.data.Product.Companion.COLLECTION_NAME
import com.finapp.api.products.model.ProductType
import org.bson.types.ObjectId
import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.mapping.*
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = COLLECTION_NAME)
data class Product(
    @MongoId val id: ObjectId? = null,
    @Field(NAME_FIELD) val name: String,
    @Field(PRICE_FIELD) val price: BigDecimal?,
    @Field(ACTIVE_FIELD) val isActive: Boolean,
    @Field(TYPE_FIELD) val type: ProductType,
    @Field(CATEGORY_FIELD) val category: String,

    @Field(IMAGES_ID_FIELD) val images: List<ObjectId>? = null,

    @CreatedDate @Field(CREATED_DATE_FIELD) val createdDate: LocalDateTime? = null,
    @LastModifiedDate @Field(LAST_MODIFIED_DATE_FIELD) var lastModifiedDate: LocalDateTime? = null,
    @LastModifiedBy @Field(LAST_MODIFIED_BY_FIELD) var lastModifiedBy: ObjectId? = null,
    @CreatedBy @Field(CREATED_BY_FIELD) var createdBy: ObjectId? = null
): Serializable {
    companion object {
        const val COLLECTION_NAME = "products"

        const val NAME_FIELD = "name"
        const val PRICE_FIELD = "price"
        const val ACTIVE_FIELD = "is_active"
        const val TYPE_FIELD = "type"
        const val CATEGORY_FIELD = "category"
        const val IMAGES_ID_FIELD = "images"
        const val COMPOSE_FIELD = "compose"

        const val CREATED_DATE_FIELD = "created_date"
        const val LAST_MODIFIED_DATE_FIELD = "last_modified_date"
        const val LAST_MODIFIED_BY_FIELD = "last_modified_by"
        const val CREATED_BY_FIELD = "created_by"
    }
}
