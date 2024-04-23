package com.finapp.api.mountable_products.data

import org.springframework.data.mongodb.core.mapping.Field
import java.math.BigDecimal

data class MountableItem(
    @Field(ACTIVE_FIELD) val isActive: Boolean,
    @Field(NAME_FIELD) val itemName: String,
    @Field(PRICE_FIELD) val price: BigDecimal?,
    @Field(MAXIMUM_QUANTITY_FIELD) val maximumQuantity: Int,
    @Field(SUBTRACTION_QUANTITY_FIELD) val subtractionQuantity: Int,
    @Field(MEASURE_FIELD) val measure: String
) {
    companion object {
        //const val COLLECTION_NAME = "mountable_items"

        const val ACTIVE_FIELD = "is_active"
        const val NAME_FIELD = "name"
        const val PRICE_FIELD = "price"
        const val MAXIMUM_QUANTITY_FIELD = "maximum_quantity"
        const val SUBTRACTION_QUANTITY_FIELD = "subtraction_quantity"
        const val MEASURE_FIELD = "measure"
    }
}
