package com.finapp.api.users.data

import org.springframework.data.mongodb.core.mapping.Field

data class Token(
    @Field(TYPE_FIELD) val type: String,
    @Field(ACCESS_FIELD) val access: String,
    @Field(REFRESH_FIELD) val refresh: String
) {
    companion object {
        const val TYPE_FIELD = "type"
        const val ACCESS_FIELD = "access"
        const val REFRESH_FIELD = "refresh"
    }
}