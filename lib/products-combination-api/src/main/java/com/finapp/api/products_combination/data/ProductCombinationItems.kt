package com.finapp.api.products_combination.data

import com.finapp.api.products_combination.data.ProductCombinationItems.Companion.COLLECTION_NAME
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import java.math.BigDecimal

@Document(collection = COLLECTION_NAME)
data class ProductCombinationItems(
    @MongoId val id: ObjectId? = null,
    @Field(NAME_FIELD) val name: String? = null,
    @Field(PRICE_FIELD) val price: BigDecimal? = null,
    @Field(QUANTITY_FIELD) val quantity: Int?,
    @Field(PRODUCTS_COMBINATION_ID_FIELD) val productsCombinationId: ObjectId? = null,

    @Field(PRODUCT_ID_FIELD) val productId: ObjectId? = null,
): Serializable {
    companion object {
        const val COLLECTION_NAME = "product_combination_items"

        const val NAME_FIELD = "name"
        const val PRICE_FIELD = "price"
        const val PRODUCTS_COMBINATION_ID_FIELD = "products_combination_id"
        const val PRODUCT_ID_FIELD = "product_id"
        const val QUANTITY_FIELD = "quantity"
    }
}
