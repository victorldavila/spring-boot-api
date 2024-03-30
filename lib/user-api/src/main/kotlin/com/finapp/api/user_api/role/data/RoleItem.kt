package com.finapp.api.user_api.role.data

import org.springframework.data.mongodb.core.mapping.Field

data class RoleItem (
    @Field(TYPE_FIELD) val type: String,
    @Field(NAME_FIELD) val name: String,
    @Field(DESCRIPTION_FIELD) val description: String? = null
){
    companion object {
        const val NAME_FIELD = "name"
        const val TYPE_FIELD = "type"
        const val DESCRIPTION_FIELD = "description"
    }
}
