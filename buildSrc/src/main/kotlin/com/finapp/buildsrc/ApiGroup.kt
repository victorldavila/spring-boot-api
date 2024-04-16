package com.finapp.buildsrc

object ApiGroup {
    const val BASE = "com.finapp.api"

    object Feature {
        const val USERS = "$BASE.users"
        const val AUTH = "$BASE.auth"
    }

    object Lib {
        const val CORE = "$BASE.core"
        const val KAFKA = "$BASE.kafka"
        const val AUTHORIZE = "$BASE.authorize"
        const val SECURITY = "$BASE.security"
        const val role = "$BASE.role"
        const val token = "$BASE.token"
        const val USERS_API = "$BASE.users"
    }
}