package com.finapp.api.user_api.data

import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable

data class Credential(
    @Field(USERNAME_FIELD) val username: String,
    @Field(PASSWORD_FIELD) val password: String
): Serializable {
    companion object {
        const val USERNAME_FIELD = "username"
        const val PASSWORD_FIELD = "password"
    }
}