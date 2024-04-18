package com.finapp.api.products.mapper

import com.finapp.api.products.data.MountableItem
import com.finapp.api.products.data.MountableStep
import com.finapp.api.products.data.Product
import com.finapp.api.products.model.*
import org.springframework.stereotype.Component

@Component
class ProductMapper {
    fun productRequestToProduct(productRequest: ProductRequest): Product = Product(
        name = productRequest.name,
        price = productRequest.price,
        isActive = productRequest.isActive,
        isVariable = productRequest.isVariable,
        category = productRequest.category,
        type = productRequest.type,
        steps = productRequest.steps?.map { it.toMountableStep() }
    )

    fun productRequestToProduct(productRequest: ProductRequest?, product: Product): Product = Product(
        id = product.id,
        name = productRequest?.name ?: product.name,
        price = getProductPriceValue(product, productRequest),
        isActive = productRequest?.isActive ?: product.isActive,
        isVariable = productRequest?.isVariable ?: product.isVariable,
        category = productRequest?.category ?: product.category,
        type = productRequest?.type ?: product.type,
        steps = productRequest?.steps?.map { it.toMountableStep() } ?: product.steps
    )

    fun productToProductResponse(product: Product): ProductResponse = ProductResponse(
        id = product.id,
        name = product.name,
        price = product.price,
        isActive = product.isActive,
        isVariable = product.isVariable,
        category = product.category,
        type = product.type,
        steps = product.steps?.map { it.toMountableStepResponse() }
    )

    private fun MountableStep.toMountableStepResponse(): MountableStepResponse = MountableStepResponse(
        name = this.stepName,
        minimum = this.minimum,
        maximum = this.maximum,
        type = this.type,
        items = this.items.map { it.toMountableItemResponse() }
    )

    private fun MountableItem.toMountableItemResponse(): MountableItemResponse = MountableItemResponse(
        name = this.itemName,
        price = this.price,
        maximumQuantity = this.maximumQuantity,
        subtractionQuantity = this.subtractionQuantity,
        measure = this.measure
    )

    private fun MountableStepRequest.toMountableStep(): MountableStep = MountableStep(
        stepName = this.name,
        minimum = this.minimum,
        maximum = this.maximum,
        type = this.type,
        items = this.items.map { it.toMountableItem() }
    )

    private fun MountableItemRequest.toMountableItem(): MountableItem = MountableItem (
        itemName = this.name,
        price = this.price,
        maximumQuantity = this.maximumQuantity,
        subtractionQuantity = this.subtractionQuantity,
        measure = this.measure
    )

    private fun getProductPriceValue(product: Product, productRequest: ProductRequest?) = productRequest?.isActive?.let {
        productRequest.getProductPrice(product)
    } ?: run { product.getProductPrice(productRequest) }

    private fun Product.getProductPrice(productRequest: ProductRequest?) =
        if (this.isVariable) { null }
        else { productRequest?.price ?: this.price }

    private fun ProductRequest?.getProductPrice(product: Product) =
        if (this?.isVariable == true) { null }
        else { this?.price ?: product.price }
}
