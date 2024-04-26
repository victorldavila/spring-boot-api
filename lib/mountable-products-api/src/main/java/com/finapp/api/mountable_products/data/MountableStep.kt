package com.finapp.api.mountable_products.data

import com.finapp.api.mountable_products.data.MountableStep.Companion.COLLECTION_NAME
import org.bson.types.ObjectId
import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.mapping.*
import java.time.LocalDateTime

@Document(collection = COLLECTION_NAME)
data class MountableStep(
    @MongoId val id: ObjectId? = null,
    @Field(PRODUCT_ID_FIELD) val productId: ObjectId?,
    @Field(ACTIVE_FIELD) val isActive: Boolean,
    @Field(NAME_FIELD) val stepName: String,
    @Field(MINIMUM_FIELD) val minimum: Int,
    @Field(MAXIMUM_FIELD) val maximum: Int,
    @Field(TYPE_FIELD) val type: String,

    @CreatedDate @Field(CREATED_DATE_FIELD) val createdDate: LocalDateTime? = null,
    @LastModifiedDate @Field(LAST_MODIFIED_DATE_FIELD) var lastModifiedDate: LocalDateTime? = null,
    @LastModifiedBy @Field(LAST_MODIFIED_BY_FIELD) var lastModifiedBy: ObjectId? = null,
    @CreatedBy @Field(CREATED_BY_FIELD) var createdBy: ObjectId? = null
) {
    companion object {
        const val COLLECTION_NAME = "mountable_steps"

        const val PRODUCT_ID_FIELD = "product_id"
        const val ACTIVE_FIELD = "is_active"
        const val NAME_FIELD = "name"
        const val MINIMUM_FIELD = "minimum"
        const val MAXIMUM_FIELD = "maximum"
        const val TYPE_FIELD = "type"

        const val CREATED_DATE_FIELD = "created_date"
        const val LAST_MODIFIED_DATE_FIELD = "last_modified_date"
        const val LAST_MODIFIED_BY_FIELD = "last_modified_by"
        const val CREATED_BY_FIELD = "created_by"
    }
}
