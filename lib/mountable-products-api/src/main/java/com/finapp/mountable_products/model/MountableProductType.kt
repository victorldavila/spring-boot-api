package com.finapp.mountable_products.model

enum class MountableProductType {
    DEFAULT,
    ADD,
    MOST_EXPENSIVE,
    AVERAGE,
    NONE;

    companion object {
        fun String.toMountableProductType(): MountableProductType = when(this) {
            DEFAULT.name -> DEFAULT
            ADD.name -> ADD
            MOST_EXPENSIVE.name -> MOST_EXPENSIVE
            AVERAGE.name -> AVERAGE
            else -> NONE
        }

        fun getAllMountableProductType() = listOf(
            DEFAULT.name,
            ADD.name,
            MOST_EXPENSIVE.name,
            AVERAGE.name,
            NONE.name
        )
    }
}