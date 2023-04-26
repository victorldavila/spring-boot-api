package com.finapp.api.user_api.token.data

import org.springframework.data.mongodb.core.mapping.Field

data class Token(
    @Field(ACCESS_FIELD) val access: String,
    @Field(REFRESH_FIELD) val refresh: String,
    @Field(VERSION_FIELD) val version: Long = 1
) {
    companion object {
        const val ACCESS_FIELD = "access"
        const val REFRESH_FIELD = "refresh"
        const val VERSION_FIELD = "version"
    }
}