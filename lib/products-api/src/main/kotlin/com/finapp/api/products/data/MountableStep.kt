package com.finapp.api.products.data

import org.springframework.data.mongodb.core.mapping.Field

data class MountableStep(
    //@Field(INDEX_FIELD) val index: Int,
    @Field(NAME_FIELD) val stepName: String,
    @Field(MINIMUM_FIELD) val minimum: Int,
    @Field(MAXIMUM_FIELD) val maximum: Int,
    @Field(TYPE_FIELD) val type: String,
    @Field(ITEMS_FIELD) val items: MutableList<MountableItem>
) {
    companion object {
        const val INDEX_FIELD = "index"
        const val NAME_FIELD = "name"
        const val MINIMUM_FIELD = "minimum"
        const val MAXIMUM_FIELD = "maximum"
        const val TYPE_FIELD = "type"
        const val ITEMS_FIELD = "items"
    }
}
