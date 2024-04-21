package com.finapp.api.products.model

enum class ProductType {
    DEFAULT,
    CHECK_IN,
    MOUNTABLE,
    SERVICE,
    NONE;

    companion object {
        fun String.toProductType(): ProductType = when(this) {
            DEFAULT.name -> DEFAULT
            CHECK_IN.name -> CHECK_IN
            MOUNTABLE.name -> MOUNTABLE
            SERVICE.name -> SERVICE
            else -> NONE
        }

        fun getAllProductType() = listOf(
            DEFAULT.name,
            CHECK_IN.name,
            MOUNTABLE.name,
            SERVICE.name,
            NONE.name
        )
    }
}