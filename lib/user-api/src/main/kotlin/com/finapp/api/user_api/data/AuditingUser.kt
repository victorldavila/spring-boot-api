package com.finapp.api.user_api.data

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Field

data class AuditingUser(
    @Field(ID_FIELD) val id: ObjectId? = null,
    @Field(FULL_NAME_FIELD) val fullName: String,
) {
    companion object {
        const val ID_FIELD = "user_id"
        const val FULL_NAME_FIELD = "full_name"
    }
}
