package com.finapp.api.products.data

import com.finapp.api.products.data.MountableStep.Companion.COLLECTION_NAME
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

//@Document(collection = COLLECTION_NAME)
data class MountableStep(
    @Field(ACTIVE_FIELD) val isActive: Boolean,
    @Field(NAME_FIELD) val stepName: String,
    @Field(MINIMUM_FIELD) val minimum: Int,
    @Field(MAXIMUM_FIELD) val maximum: Int,
    @Field(TYPE_FIELD) val type: String,
    @Field(ITEMS_FIELD) val items: MutableList<MountableItem>
) {
    companion object {
        const val COLLECTION_NAME = "mountable_steps"

        const val ACTIVE_FIELD = "is_active"
        const val NAME_FIELD = "name"
        const val MINIMUM_FIELD = "minimum"
        const val MAXIMUM_FIELD = "maximum"
        const val TYPE_FIELD = "type"
        const val ITEMS_FIELD = "items"
    }
}
