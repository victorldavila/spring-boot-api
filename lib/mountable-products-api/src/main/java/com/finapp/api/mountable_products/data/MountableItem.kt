package com.finapp.api.mountable_products.data

import com.finapp.api.mountable_products.data.MountableItem.Companion.COLLECTION_NAME
import com.finapp.api.mountable_products.data.MountableStep.Companion.PRODUCT_ID_FIELD
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.math.BigDecimal

@Document(collection = COLLECTION_NAME)
data class MountableItem(
    @MongoId val id: ObjectId? = null,
    @Field(ACTIVE_FIELD) val isActive: Boolean,
    @Field(MOUNTABLE_STEP_ID_FIELD) val mountableStepId: ObjectId?,
    @Field(NAME_FIELD) val itemName: String,
    @Field(PRICE_FIELD) val price: BigDecimal?,
    @Field(MAXIMUM_QUANTITY_FIELD) val maximumQuantity: Int,
    @Field(SUBTRACTION_QUANTITY_FIELD) val subtractionQuantity: Int,
    @Field(MEASURE_FIELD) val measure: String
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
    }
}
