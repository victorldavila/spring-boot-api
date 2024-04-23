package com.finapp.mountable_products.mapper

import com.finapp.mountable_products.data.MountableItem
import com.finapp.mountable_products.data.MountableStep
import com.finapp.mountable_products.model.MountableItemRequest
import com.finapp.mountable_products.model.MountableItemResponse
import com.finapp.mountable_products.model.MountableStepRequest
import com.finapp.mountable_products.model.MountableStepResponse
import org.springframework.stereotype.Component

@Component
class MountableProductMapper {
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
        isActive = this.isActive!!,
        items = this.items!!.map { it.toMountableItem() }.toMutableList()
    )

    private fun MountableItemRequest.toMountableItem(): MountableItem = MountableItem (
        itemName = this.name!!,
        price = this.price,
        maximumQuantity = this.maximumQuantity!!,
        subtractionQuantity = this.subtractionQuantity!!,
        isActive = this.isActive!!,
        measure = this.measure!!
    )
}
