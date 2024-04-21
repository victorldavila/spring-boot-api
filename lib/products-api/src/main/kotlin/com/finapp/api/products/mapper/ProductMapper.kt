package com.finapp.api.products.mapper

import com.finapp.api.products.data.MountableItem
import com.finapp.api.products.data.MountableStep
import com.finapp.api.products.data.Product
import com.finapp.api.products.model.*
import com.finapp.api.products.model.ProductType.Companion.toProductType
import org.springframework.stereotype.Component

@Component
class ProductMapper {
    fun productRequestToProduct(productRequest: ProductRequest): Product = Product(
        name = productRequest.name!!,
        price = productRequest.price,
        isActive = productRequest.isActive!!,
        category = productRequest.category!!,
        type = productRequest.type!!.toProductType(),
        compose = productRequest.composed?.map { it.toMountableStep() },
        steps = productRequest.steps?.map { it.toMountableStep() }?.toMutableList()
    )

    fun productRequestToProduct(productRequest: ProductRequest?, product: Product): Product = product.copy(
        name = productRequest?.name ?: product.name,
        price = productRequest?.getProductPrice(product),
        isActive = productRequest?.isActive ?: product.isActive,
        category = productRequest?.category ?: product.category,
        type = productRequest?.type?.toProductType() ?: product.type,
        compose = productRequest?.composed?.map { it.toMountableStep() },
        steps = with(product.steps) {
            productRequest?.steps?.forEach {
                val newItem = it.toMountableStep(this!![it.index])
                this.removeAt(it.index)
                this.add(it.index, newItem)
            }

            return@with this
        }
    )

    fun productToProductResponse(product: Product): ProductResponse = ProductResponse(
        id = product.id?.toHexString(),
        name = product.name,
        price = product.price,
        isActive = product.isActive,
        category = product.category,
        type = product.type.name,
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

    private fun MountableStepRequest.toMountableStep(mountableStep: MountableStep): MountableStep = mountableStep.copy(
        stepName = this.name ?: mountableStep.stepName,
        minimum = this.minimum ?: mountableStep.minimum,
        maximum = this.maximum ?: mountableStep.maximum,
        type = this.type ?: mountableStep.type,
        items = with(mountableStep.items) {
            this@toMountableStep.items?.forEach {
                val newItem = it.toMountableItem(this[it.index])
                this.removeAt(it.index)
                this.add(it.index, newItem)
            }

            return@with this
        }
    )

    private fun MountableItemRequest.toMountableItem(mountableItem: MountableItem): MountableItem = mountableItem.copy(
        itemName = this.name ?: mountableItem.itemName,
        price = if (this.isVariable == true) {
            null
        } else {
            this.price ?: mountableItem.price
        },
        maximumQuantity = this.maximumQuantity ?: mountableItem.maximumQuantity,
        subtractionQuantity = this.subtractionQuantity ?: mountableItem.subtractionQuantity,
        measure = this.measure ?: mountableItem.measure
    )

    private fun MountableStepRequest.toMountableStep(): MountableStep = MountableStep(
        stepName = this.name!!,
        minimum = this.minimum!!,
        maximum = this.maximum!!,
        type = this.type!!,
        items = this.items!!.map { it.toMountableItem() }.toMutableList()
    )

    private fun MountableItemRequest.toMountableItem(): MountableItem = MountableItem (
        itemName = this.name!!,
        price = this.price,
        maximumQuantity = this.maximumQuantity!!,
        subtractionQuantity = this.subtractionQuantity!!,
        measure = this.measure!!
    )

    private fun ProductRequest?.getProductPrice(product: Product) =
        if (this?.isVariable == true) { null }
        else { this?.price ?: product.price }
}
