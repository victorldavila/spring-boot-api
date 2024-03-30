package com.finapp.api.user_api.role.data

import com.finapp.api.user_api.data.AuditingUser
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

@Document(collection = "role")
data class Role(
    @MongoId val id: ObjectId? = null,
    @Field(USER_ID_FIELD) val userId: ObjectId?,
    @Field(IS_ACTIVE_FIELD) val isActive: Boolean = true,
    @Field(API_ROLE_FIELD) val roleItems: List<RoleItem>? = null,
    @Field(FEATURE_TOGGLE_FIELD) val featureToggles: List<RoleItem>? = null,

    @CreatedDate
    @Field(CREATED_DATE_FIELD) val createdDate: LocalDateTime? = null,
    @LastModifiedDate
    @Field(LAST_MODIFIED_DATE_FIELD) var lastModifiedDate: LocalDateTime? = null,
    @LastModifiedBy
    @Field(LAST_MODIFIED_BY_FIELD) var lastModifiedBy: AuditingUser? = null,
    @CreatedBy
    @Field(CREATED_BY_FIELD) var createdBy: AuditingUser? = null
): Serializable {

    companion object {
        const val API_ROLE_FIELD = "api_roles"
        const val FEATURE_TOGGLE_FIELD = "feature_toggle"
        const val USER_ID_FIELD = "user_id"
        const val IS_ACTIVE_FIELD = "is_active"

        const val CREATED_DATE_FIELD = "created_date"
        const val LAST_MODIFIED_DATE_FIELD = "last_modified_date"
        const val LAST_MODIFIED_BY_FIELD = "last_modified_by"
        const val CREATED_BY_FIELD = "created_by"
    }
}
