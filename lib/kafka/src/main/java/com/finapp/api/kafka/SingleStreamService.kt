package com.finapp.api.kafka

import org.springframework.stereotype.Component

@Component
class SingleStreamService {

    fun enrichMessage(message: ProductMessage): InventoryInfo = InventoryInfo(message.name + " plus it")
}