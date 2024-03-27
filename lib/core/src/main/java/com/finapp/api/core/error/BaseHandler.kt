package com.finapp.api.core.error

import jakarta.validation.ConstraintViolationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

abstract class BaseHandler(
    private val validationHandler: ValidationHandler
) {
    fun errorResponse(error: Throwable): Mono<ServerResponse> = when(error) {
        is ConstraintViolationException,
        is MethodArgumentNotValidException,
        is BadRequestError -> ServerResponse.badRequest().body(errorHandling(error))
        is NotFoundError -> ServerResponse.notFound().build()
        else -> throw error
    }

    private fun errorHandling(error: Throwable) = BodyInserters.fromValue(when(error) {
        is ConstraintViolationException -> validationHandler.onConstraintValidationException(error)
        is MethodArgumentNotValidException -> validationHandler.onMethodArgumentNotValidException(error)
        is BadRequestError -> ValidationErrorResponse().apply { this.error.add(ViolationResponse(message = error.message)) }
        else -> throw error
    })
}