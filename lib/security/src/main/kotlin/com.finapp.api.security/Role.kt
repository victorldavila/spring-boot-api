package com.finapp.api.security

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId

data class Role (
    @MongoId val id: ObjectId? = null,
    @Field(TYPE_FIELD) val type: String,
    @Field(NAME_FIELD) val name: String
){
    companion object {
        const val NAME_FIELD = "name"
        const val TYPE_FIELD = "type"
    }
}
