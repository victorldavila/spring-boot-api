package com.finapp.buildsrc

object ApiGroup {
    const val base = "com.finapp.api"
    object Feature {
        const val user = "$base.user"
        const val auth = "$base.auth"
    }

    object Lib {
        const val core = "$base.core"
        const val authorize = "$base.authorize"
        const val security = "$base.security"
        const val role = "$base.role"
        const val token = "$base.token"
        const val userInterface = "$base.user_api"
    }
}