package com.finapp.api.products_combination.mapper

import com.finapp.api.products_combination.data.ProductsCombination
import com.finapp.api.products_combination.model.ProductsCombinationRequest
import com.finapp.api.products_combination.model.ProductsCombinationResponse
import org.springframework.stereotype.Component

@Component
class ProductsCombinationMapper {
    fun productsCombinationRequestToProductsCombination(
        productsCombinationRequest: ProductsCombinationRequest
    ): ProductsCombination =
        ProductsCombination(
            name = productsCombinationRequest.name!!,
            price = productsCombinationRequest.price,
            isActive = productsCombinationRequest.isActive!!,
            type = productsCombinationRequest.type!!,
            category = productsCombinationRequest.category!!
        )

    fun productsCombinationRequestToProductsCombination(
        productsCombinationRequest: ProductsCombinationRequest?,
        productsCombination: ProductsCombination
    ): ProductsCombination =
        ProductsCombination(
            name = productsCombinationRequest?.name ?: productsCombination.name,
            price = productsCombinationRequest?.price ?: productsCombination.price,
            isActive = productsCombinationRequest?.isActive ?: productsCombination.isActive,
            type = productsCombinationRequest?.type ?: productsCombination.type,
            category = productsCombinationRequest?.category ?: productsCombination.category,
        )

    fun productsCombinationToProductsCombinationResponse(
        productsCombination: ProductsCombination
    ): ProductsCombinationResponse = ProductsCombinationResponse(
        id = productsCombination.id?.toHexString(),
        name = productsCombination.name,
        price = productsCombination.price,
        isActive = productsCombination.isActive,
        type = productsCombination.type,
        category = productsCombination.category
    )
}
