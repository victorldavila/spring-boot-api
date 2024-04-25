package com.finapp.api.mountable_products.mapper

import com.finapp.api.mountable_products.data.MountableItem
import com.finapp.api.mountable_products.data.MountableStep
import com.finapp.api.mountable_products.model.MountableItemRequest
import com.finapp.api.mountable_products.model.MountableItemResponse
import com.finapp.api.mountable_products.model.MountableStepRequest
import com.finapp.api.mountable_products.model.MountableStepResponse
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class MountableProductMapper {
    fun mountableStepToMountableStepResponse(mountableStep: MountableStep): MountableStepResponse = MountableStepResponse(
        id = mountableStep.id?.toHexString(),
        name = mountableStep.stepName,
        minimum = mountableStep.minimum,
        maximum = mountableStep.maximum,
        type = mountableStep.type,
        items = mountableStep.items.map { it.toMountableItemResponse() }
    )

    private fun MountableItem.toMountableItemResponse(): MountableItemResponse = MountableItemResponse(
        name = this.itemName,
        price = this.price,
        maximumQuantity = this.maximumQuantity,
        subtractionQuantity = this.subtractionQuantity,
        measure = this.measure
    )

    fun mountableStepRequestToMountableStep(mountableStepRequest: MountableStepRequest, mountableStep: MountableStep): MountableStep = mountableStep.copy(
        stepName = mountableStepRequest.name ?: mountableStep.stepName,
        minimum = mountableStepRequest.minimum ?: mountableStep.minimum,
        maximum = mountableStepRequest.maximum ?: mountableStep.maximum,
        type = mountableStepRequest.type ?: mountableStep.type,
        items = with(mountableStep.items) {
            mountableStepRequest.items?.forEach {
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

    fun mountableStepRequestToMountableStep(mountableStepRequest: MountableStepRequest, productId: ObjectId? = null): MountableStep = MountableStep(
        productId = productId,
        stepName = mountableStepRequest.name!!,
        minimum = mountableStepRequest.minimum!!,
        maximum = mountableStepRequest.maximum!!,
        type = mountableStepRequest.type!!,
        isActive = mountableStepRequest.isActive!!,
        items = mountableStepRequest.items!!.map { it.toMountableItem() }.toMutableList()
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
