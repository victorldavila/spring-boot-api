package com.finapp.api.mountable_products.mapper

import com.finapp.api.mountable_products.data.MountableItem
import com.finapp.api.mountable_products.model.MountableItemParam
import com.finapp.api.mountable_products.model.MountableItemRequest
import com.finapp.api.mountable_products.model.MountableItemResponse
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class MountableItemMapper {
    fun mountableItemToMountableItemResponse(mountableItem: MountableItem): MountableItemResponse = MountableItemResponse(
        id = mountableItem.id?.toHexString(),
        name = mountableItem.itemName,
        price = mountableItem.price,
        maximumQuantity = mountableItem.maximumQuantity,
        subtractionQuantity = mountableItem.subtractionQuantity,
        measure = mountableItem.measure
    )

    fun mountableItemRequestToMountableItem(mountableItemRequest: MountableItemRequest, mountableItem: MountableItem): MountableItem = mountableItem.copy(
        itemName = mountableItemRequest.name ?: mountableItem.itemName,
        price = if (mountableItemRequest.isVariable == true) {
            null
        } else {
            mountableItemRequest.price ?: mountableItem.price
        },
        maximumQuantity = mountableItemRequest.maximumQuantity ?: mountableItem.maximumQuantity,
        subtractionQuantity = mountableItemRequest.subtractionQuantity ?: mountableItem.subtractionQuantity,
        measure = mountableItemRequest.measure ?: mountableItem.measure
    )

    fun mountableItemRequestToMountableItem(mountableItemRequest: MountableItemRequest, mountableItemParam: MountableItemParam?): MountableItem = MountableItem (
        mountableStepId = ObjectId(mountableItemParam?.mountableStepId),
        itemName = mountableItemRequest.name!!,
        price = mountableItemRequest.price,
        maximumQuantity = mountableItemRequest.maximumQuantity!!,
        subtractionQuantity = mountableItemRequest.subtractionQuantity!!,
        isActive = mountableItemRequest.isActive!!,
        measure = mountableItemRequest.measure!!
    )
}