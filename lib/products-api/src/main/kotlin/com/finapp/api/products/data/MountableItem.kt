package com.finapp.api.products.data

import com.finapp.api.products.data.MountableStep.Companion
import org.springframework.data.mongodb.core.mapping.Field
import java.math.BigDecimal

data class MountableItem(
    //@Field(INDEX_FIELD) val index: Int,
    @Field(NAME_FIELD) val itemName: String,
    @Field(PRICE_FIELD) val price: BigDecimal?,
    @Field(MAXIMUM_QUANTITY_FIELD) val maximumQuantity: Int,
    @Field(SUBTRACTION_QUANTITY_FIELD) val subtractionQuantity: Int,
    @Field(MEASURE_FIELD) val measure: String
) {
    companion object {
        const val INDEX_FIELD = "index"
        const val NAME_FIELD = "name"
        const val PRICE_FIELD = "price"
        const val MAXIMUM_QUANTITY_FIELD = "maximum_quantity"
        const val SUBTRACTION_QUANTITY_FIELD = "subtraction_quantity"
        const val MEASURE_FIELD = "measure"
    }
}
