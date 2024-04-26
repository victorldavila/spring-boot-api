package com.finapp.api.mountable_products.data

import com.finapp.api.mountable_products.data.MountableItem.Companion.COLLECTION_NAME
import com.finapp.api.mountable_products.data.MountableStep.Companion.CREATED_BY_FIELD
import com.finapp.api.mountable_products.data.MountableStep.Companion.CREATED_DATE_FIELD
import com.finapp.api.mountable_products.data.MountableStep.Companion.LAST_MODIFIED_BY_FIELD
import com.finapp.api.mountable_products.data.MountableStep.Companion.LAST_MODIFIED_DATE_FIELD
import com.finapp.api.mountable_products.data.MountableStep.Companion.PRODUCT_ID_FIELD
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = COLLECTION_NAME)
data class MountableItem(
    @MongoId val id: ObjectId? = null,
    @Field(ACTIVE_FIELD) val isActive: Boolean,
    @Field(MOUNTABLE_STEP_ID_FIELD) val mountableStepId: ObjectId?,
    @Field(NAME_FIELD) val itemName: String,
    @Field(PRICE_FIELD) val price: BigDecimal?,
    @Field(MAXIMUM_QUANTITY_FIELD) val maximumQuantity: Int,
    @Field(SUBTRACTION_QUANTITY_FIELD) val subtractionQuantity: Int,
    @Field(MEASURE_FIELD) val measure: String,

    @CreatedDate @Field(CREATED_DATE_FIELD) val createdDate: LocalDateTime? = null,
    @LastModifiedDate @Field(LAST_MODIFIED_DATE_FIELD) var lastModifiedDate: LocalDateTime? = null,
    @LastModifiedBy @Field(LAST_MODIFIED_BY_FIELD) var lastModifiedBy: ObjectId? = null,
    @CreatedBy @Field(CREATED_BY_FIELD) var createdBy: ObjectId? = null
) {
    companion object {
        const val COLLECTION_NAME = "mountable_items"

        const val MOUNTABLE_STEP_ID_FIELD = "mountable_step_id"
        const val ACTIVE_FIELD = "is_active"
        const val NAME_FIELD = "name"
        const val PRICE_FIELD = "price"
        const val MAXIMUM_QUANTITY_FIELD = "maximum_quantity"
        const val SUBTRACTION_QUANTITY_FIELD = "subtraction_quantity"
        const val MEASURE_FIELD = "measure"

        const val CREATED_DATE_FIELD = "created_date"
        const val LAST_MODIFIED_DATE_FIELD = "last_modified_date"
        const val LAST_MODIFIED_BY_FIELD = "last_modified_by"
        const val CREATED_BY_FIELD = "created_by"
    }
}
