package com.finapp.api.mountable_products.mapper

import com.finapp.api.mountable_products.data.MountableStep
import com.finapp.api.mountable_products.model.MountableStepArg
import com.finapp.api.mountable_products.model.MountableStepRequest
import com.finapp.api.mountable_products.model.MountableStepResponse
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class MountableStepMapper {
    fun mountableStepToMountableStepResponse(mountableStep: MountableStep): MountableStepResponse = MountableStepResponse(
        id = mountableStep.id?.toHexString(),
        name = mountableStep.stepName,
        minimum = mountableStep.minimum,
        maximum = mountableStep.maximum,
        type = mountableStep.type
    )

    fun mountableStepRequestToMountableStep(mountableStepRequest: MountableStepRequest, mountableStep: MountableStep): MountableStep = mountableStep.copy(
        stepName = mountableStepRequest.name ?: mountableStep.stepName,
        minimum = mountableStepRequest.minimum ?: mountableStep.minimum,
        maximum = mountableStepRequest.maximum ?: mountableStep.maximum,
        type = mountableStepRequest.type ?: mountableStep.type
    )

    fun mountableStepRequestToMountableStep(mountableStepRequest: MountableStepRequest, mountableStepArg: MountableStepArg? = null): MountableStep = MountableStep(
        productId = ObjectId(mountableStepArg?.mountableStepParam?.productId),
        stepName = mountableStepRequest.name!!,
        minimum = mountableStepRequest.minimum!!,
        maximum = mountableStepRequest.maximum!!,
        type = mountableStepRequest.type!!,
        isActive = mountableStepRequest.isActive!!
    )
}
