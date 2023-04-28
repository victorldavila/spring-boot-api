package com.finapp.api.core.error

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*


@Component
class ValidationHandler {
    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onConstraintValidationException(
        e: ConstraintViolationException
    ): ValidationErrorResponse {
        val error = ValidationErrorResponse()
        for (violation in e.constraintViolations) {
            error.error.add(
                ViolationResponse(violation.message)
            )
        }
        return error
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onMethodArgumentNotValidException(
        e: MethodArgumentNotValidException
    ): ValidationErrorResponse {
        val error = ValidationErrorResponse()
        for (fieldError in e.bindingResult.fieldErrors) {
            error.error.add(
                ViolationResponse(fieldError.defaultMessage)
            )
        }
        return error
    }
}