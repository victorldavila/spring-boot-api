package com.finapp.api.mountable_products.model

enum class MountableStepType {
    DEFAULT,
    ADD,
    MOST_EXPENSIVE,
    AVERAGE,
    NONE;

    companion object {
        fun String.toMountableProductType(): MountableStepType = when(this) {
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