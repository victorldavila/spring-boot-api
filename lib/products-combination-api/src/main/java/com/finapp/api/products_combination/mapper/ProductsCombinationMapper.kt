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
            price = if (productsCombinationRequest.isVariable == false) {
                productsCombinationRequest.price
            } else {
                null
            },
            isActive = productsCombinationRequest.isActive!!,
            type = productsCombinationRequest.type!!,
            category = productsCombinationRequest.category!!
        )

    fun productsCombinationRequestToProductsCombination(
        productsCombinationRequest: ProductsCombinationRequest?,
        productsCombination: ProductsCombination
    ): ProductsCombination =
        productsCombination.copy(
            name = productsCombinationRequest?.name ?: productsCombination.name,
            price = if (productsCombinationRequest?.isVariable == false) {
                productsCombinationRequest.price ?: productsCombination.price
            } else {
                null
            },
            isActive = productsCombinationRequest?.isActive ?: productsCombination.isActive,
            type = productsCombinationRequest?.type ?: productsCombination.type,
            category = productsCombinationRequest?.category ?: productsCombination.category,
        )

    fun productsCombinationToProductsCombinationResponse(
        productsCombination: ProductsCombination
    ): ProductsCombinationResponse = ProductsCombinationResponse(
        id = productsCombination.id?.toHexString(),
        name = productsCombination.name,
        price =  productsCombination.price,
        isActive = productsCombination.isActive,
        type = productsCombination.type,
        category = productsCombination.category
    )
}
