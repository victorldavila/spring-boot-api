package com.finapp.api.core

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.RequestPredicates

object ApiRouter {
    fun apiPOST(path: String): RequestPredicate =
        RequestPredicates.method(HttpMethod.POST)
            .and(RequestPredicates.path(path))
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
            .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON))

    fun apiPUT(path: String): RequestPredicate =
        RequestPredicates.method(HttpMethod.PUT)
            .and(RequestPredicates.path(path))
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
            .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON))

    fun apiDELETE(path: String): RequestPredicate =
        RequestPredicates.method(HttpMethod.DELETE)
            .and(RequestPredicates.path(path))
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))

    fun apiGET(path: String): RequestPredicate =
        RequestPredicates.method(HttpMethod.GET)
            .and(RequestPredicates.path(path))
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
}