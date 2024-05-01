package com.finapp.api.products_combination.mapper

import com.finapp.api.products_combination.data.ProductCombinationItems
import com.finapp.api.products_combination.model.ProductCombinationItemRequest
import com.finapp.api.products_combination.model.ProductCombinationItemResponse
import com.finapp.api.products_combination.model.ProductCombinationItemsParam
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class ProductCombinationItemsMapper {
    fun productCombinationItemRequestToProductCombinationItem(
        productCombinationItemRequest: ProductCombinationItemRequest?,
        productCombinationItemsParam: ProductCombinationItemsParam?
    ) = ProductCombinationItems(
        name = productCombinationItemRequest?.name,
        price = if (productCombinationItemRequest?.isVariable == true) {
            productCombinationItemRequest.price
        } else {
            null
        },
        quantity = productCombinationItemRequest?.quantity,
        productId = ObjectId(productCombinationItemRequest?.productId),
        productsCombinationId = ObjectId(productCombinationItemsParam?.productCombinationId)
    )

    fun productCombinationItemRequestToProductCombinationItem(
        productCombinationItemRequest: ProductCombinationItemRequest?,
        productCombinationItems: ProductCombinationItems
    ) =
        productCombinationItems.copy(
            name = productCombinationItemRequest?.name ?: productCombinationItems.name,
            price = if (productCombinationItemRequest?.isVariable == true) {
                productCombinationItemRequest.price ?: productCombinationItems.price
            } else {
                null
            },
            quantity = productCombinationItemRequest?.quantity ?: productCombinationItems.quantity,
            productId = productCombinationItemRequest?.productId?.let { ObjectId(it) } ?: productCombinationItems.productId
        )

    fun productCombinationItemToProductCombinationItemResponse(
        productCombinationItems: ProductCombinationItems?
    ): ProductCombinationItemResponse = ProductCombinationItemResponse(
        id = productCombinationItems?.id?.toHexString(),
        name = productCombinationItems?.name,
        price = productCombinationItems?.price,
        quantity = productCombinationItems?.quantity
    )
}