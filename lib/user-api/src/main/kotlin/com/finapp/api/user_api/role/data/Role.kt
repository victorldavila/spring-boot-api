package com.finapp.api.user_api.role.data

import org.springframework.data.mongodb.core.mapping.Field

data class Role (
    @Field(TYPE_FIELD) val type: String,
    @Field(NAME_FIELD) val name: String
){
    companion object {
        const val NAME_FIELD = "name"
        const val TYPE_FIELD = "type"
    }
}
