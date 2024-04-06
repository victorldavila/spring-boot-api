package com.finapp.api.core.websocket

data class WSMessage(
    val uniqueId: String?,
    val message: Any
)
