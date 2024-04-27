package com.finapp.api.core

import org.springframework.web.reactive.function.server.ServerRequest

fun ServerRequest.tryGetPathVariable(pathParam: String): String? {
    var paramId: String? = null

    try {
        paramId = this.pathVariable(pathParam)
    } catch (error: Exception) { }

    return paramId
}