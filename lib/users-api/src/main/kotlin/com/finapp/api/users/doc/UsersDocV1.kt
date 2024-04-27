package com.finapp.api.users.doc

import com.finapp.api.users.model.UserRequest
import com.finapp.api.users.model.UserResponse
import com.finapp.api.users.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@RouterOperations(
    RouterOperation(
        path = "/v1/users",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.GET],
        beanClass = UserService::class,
        beanMethod = "getAllUsers",
        operation = Operation(
            operationId = "updateUser",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = [Content(array = ArraySchema(schema = Schema(implementation = UserResponse::class)))]
                )
            ]
        )
    ),
    RouterOperation(
        path = "/v1/users/{userId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.GET],
        beanClass = UserService::class,
        beanMethod = "getUserById",
        operation = Operation(
            operationId = "getUserById",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content =  [Content(schema = Schema(implementation = UserResponse::class))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid User id"),
                ApiResponse(responseCode = "404", description = "User not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "userId")],
            requestBody = RequestBody(content = [Content()])
        )
    ),
    RouterOperation(
        path = "/v1/users/{userId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.PUT],
        beanClass = UserService::class,
        beanMethod = "updateUser",
        operation = Operation(
            operationId = "updateUser",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = [Content(schema = Schema(implementation = UserResponse::class))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid User"),
                ApiResponse(responseCode = "404", description = "User not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "userId")],
            requestBody = RequestBody(content = [Content(schema = Schema(implementation = UserRequest::class))])
        )
    ),
    RouterOperation(
        path = "/v1/users/{userId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.DELETE],
        beanClass = UserService::class,
        beanMethod = "deleteUser",
        operation = Operation(
            operationId = "deleteUser",
            responses = [
                ApiResponse(
                    responseCode = "201",
                    description = "successful operation",
                ),
                ApiResponse(responseCode = "400", description = "Invalid User Id"),
                ApiResponse(responseCode = "404", description = "User not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "userId")],
            requestBody = RequestBody(content = [Content()])
        )
    )
)
annotation class UsersDocV1