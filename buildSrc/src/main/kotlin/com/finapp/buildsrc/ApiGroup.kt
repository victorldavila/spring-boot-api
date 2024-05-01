package com.finapp.buildsrc

object ApiGroup {
    const val BASE = "com.finapp.api"

    object Feature {
        const val USERS = "$BASE.users"
        const val PRODUCTS = "$BASE.products"
        const val PRODUCTS_COMBINATION = "$BASE.products_combination"
        const val AUTH = "$BASE.auth"
        const val MOUNTABLE_PRODUCTS = "$BASE.mountable_products"
    }

    object Lib {
        const val MOUNTABLE_PRODUCTS_API = "$BASE.mountable_products"
        const val STOCKS_API = "$BASE.stocks"
        const val CORE = "$BASE.core"
        const val KAFKA = "$BASE.kafka"
        const val AUTHORIZE = "$BASE.authorize"
        const val SECURITY = "$BASE.security"
        const val ROLE = "$BASE.role"
        const val TOKEN = "$BASE.token"
        const val USERS_API = "$BASE.users"
        const val PRODUCTS_API = "$BASE.products"
        const val PRODUCTS_COMBINATION_API = "$BASE.products_combination"
    }
}