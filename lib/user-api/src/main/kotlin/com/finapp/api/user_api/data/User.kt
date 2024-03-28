package com.finapp.api.user_api.data

import com.finapp.api.user_api.credential.data.Credential
import com.finapp.api.user_api.role.data.Role
import com.finapp.api.user_api.token.data.Token
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import java.time.LocalDateTime

@Document(collection = "user")
data class User(
    @MongoId val id: ObjectId? = null,
    @Field(EMAIL_FIELD) val email: String,
    @Field(FIRST_NAME_FIELD) val firstName: String,
    @Field(LAST_NAME_FIELD) val lastName: String,
    @Field(CREDENTIAL_FIELD) val credential: Credential? = null,
    @Field(TOKEN_FIELD) val token: List<Token> = emptyList(),
    @Field(ROLE_FIELD) val roles: List<Role> = emptyList(),

    @CreatedDate
    @Field(CREATED_DATE_FIELD) val createdDate: LocalDateTime? = null,
    @LastModifiedDate
    @Field(LAST_MODIFIED_DATE_FIELD) var lastModifiedDate: LocalDateTime? = null,
    @LastModifiedBy
    @Field(LAST_MODIFIED_BY_FIELD) var lastModifiedBy: ObjectId? = null,
    @CreatedBy
    @Field(CREATED_BY_FIELD) var createdBy: ObjectId? = null
): Serializable {

    companion object {
        const val TOKEN_FIELD = "token"
        const val ROLE_FIELD = "role"
        const val EMAIL_FIELD = "email"
        const val FIRST_NAME_FIELD = "first_name"
        const val LAST_NAME_FIELD = "last_name"
        const val CREDENTIAL_FIELD = "credential"
        const val CREATED_DATE_FIELD = "created_date"
        const val LAST_MODIFIED_DATE_FIELD = "last_modified_date"
        const val LAST_MODIFIED_BY_FIELD = "last_modified_by"
        const val CREATED_BY_FIELD = "created_by"
    }
}
