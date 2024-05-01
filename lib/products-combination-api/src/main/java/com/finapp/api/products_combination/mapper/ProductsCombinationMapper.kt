package com.finapp.api.products_combination.mapper

import com.finapp.api.products_combination.data.ProductCombinationItems
import com.finapp.api.products_combination.data.ProductsCombination
import com.finapp.api.products_combination.model.ProductCombinationItemRequest
import com.finapp.api.products_combination.model.ProductCombinationItemResponse
import com.finapp.api.products_combination.model.ProductsCombinationRequest
import com.finapp.api.products_combination.model.ProductsCombinationResponse
import org.bson.types.ObjectId
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
            category = productsCombinationRequest.category!!,
            //products = productsCombinationRequest.products?.map { it.toProductCombinationItem() }
        )

    private fun ProductCombinationItemRequest.toProductCombinationItem() = ProductCombinationItems(
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        productId = ObjectId(this.productId)
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
//            products = productsCombinationRequest?.products?.map { productRequest ->
//                productRequest.toProductCombinationItem(
//                    productsCombination.products?.firstOrNull { it.productId?.toHexString() == productRequest.productId }
//                )
//            } ?: productsCombination.products
        )

    private fun ProductCombinationItemRequest.toProductCombinationItem(productCombinationItems: ProductCombinationItems?) =
        ProductCombinationItems(
            name = this.name ?: productCombinationItems?.name,
            price = this.price ?: productCombinationItems?.price,
            quantity = this.quantity ?: productCombinationItems?.quantity,
            productId = ObjectId(this.productId)
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

    fun productCombinationItemToProductCombinationItemResponse(
        productCombinationItems: ProductCombinationItems?
    ): ProductCombinationItemResponse = ProductCombinationItemResponse(
        name = productCombinationItems?.name,
        price = productCombinationItems?.price,
        quantity = productCombinationItems?.quantity
    )
}
