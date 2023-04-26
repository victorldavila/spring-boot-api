package com.finapp.api.user_api.data

import com.finapp.api.user_api.credential.data.Credential
import com.finapp.api.user_api.role.data.Role
import com.finapp.api.user_api.token.data.Token
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable

@Document(collection = "user")
data class User(
    @MongoId val id: ObjectId? = null,
    @Field(EMAIL_FIELD) val email: String,
    @Field(FIRST_NAME_FIELD) val firstName: String,
    @Field(LAST_NAME_FIELD) val lastName: String,
    @Field(CREDENTIAL_FIELD) val credential: Credential,
    @Field(TOKEN_FIELD) val token: List<Token> = emptyList(),
    @Field(ROLE_FIELD) val roles: List<Role> = emptyList(),
    @Field(VERSION_FIELD) val version: Long = 1
): Serializable {
    companion object {
        const val TOKEN_FIELD = "token"
        const val ROLE_FIELD = "role"
        const val EMAIL_FIELD = "email"
        const val FIRST_NAME_FIELD = "first_name"
        const val LAST_NAME_FIELD = "last_name"
        const val CREDENTIAL_FIELD = "credential"
        const val VERSION_FIELD = "version"
    }
}
