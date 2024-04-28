package com.finapp.api.mountable_products.doc

import com.finapp.api.mountable_products.model.MountableItemRequest
import com.finapp.api.mountable_products.model.MountableItemResponse
import com.finapp.api.mountable_products.model.MountableStepRequest
import com.finapp.api.mountable_products.model.MountableStepResponse
import com.finapp.api.mountable_products.service.MountableItemService
import com.finapp.api.mountable_products.service.MountableStepService
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
        path = "/v1/products/{productId}/steps",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.GET],
        beanClass = MountableStepService::class,
        beanMethod = "getMountableStepByProductId",
        operation = Operation(
            operationId = "getMountableStepByProductId",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = [Content(array = ArraySchema(schema = Schema(implementation = MountableStepResponse::class)))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid Products Id"),
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "productId")]
        )
    ),
    RouterOperation(
        path = "/v1/products/steps/{mountableStepId}/items",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.GET],
        beanClass = MountableItemService::class,
        beanMethod = "getMountableItemsByMountableStepId",
        operation = Operation(
            operationId = "getMountableItemsByMountableStepId",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = [Content(array = ArraySchema(schema = Schema(implementation = MountableItemResponse::class)))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid Mountable Step Id"),
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "productId")]
        )
    ),
    RouterOperation(
        path = "/v1/products/steps/{mountableStepId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.GET],
        beanClass = MountableStepService::class,
        beanMethod = "getMountableStepById",
        operation = Operation(
            operationId = "getMountableStepById",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content =  [Content(schema = Schema(implementation = MountableStepResponse::class))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid Mountable Step Id"),
                ApiResponse(responseCode = "404", description = "Mountable Step not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "mountableStepId")],
            requestBody = RequestBody(content = [Content()])
        )
    ),
    RouterOperation(
        path = "/v1/products/steps/items/{mountableItemId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.GET],
        beanClass = MountableItemService::class,
        beanMethod = "getMountableItemsById",
        operation = Operation(
            operationId = "getMountableItemsById",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content =  [Content(schema = Schema(implementation = MountableItemResponse::class))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid Mountable Item Id"),
                ApiResponse(responseCode = "404", description = "Mountable Item not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "mountableItemId")],
            requestBody = RequestBody(content = [Content()])
        )
    ),
    RouterOperation(
        path = "/v1/products/{productId}/steps",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.POST],
        beanClass = MountableStepService::class,
        beanMethod = "createMountableStep",
        operation = Operation(
            operationId = "createMountableStep",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = [Content(schema = Schema(implementation = MountableStepResponse::class))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid Mountable Step"),
                ApiResponse(responseCode = "404", description = "product not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "productId")],
            requestBody = RequestBody(content = [Content(schema = Schema(implementation = MountableStepRequest::class))])
        )
    ),
    RouterOperation(
        path = "/v1/products/steps/{mountableStep}/items",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.POST],
        beanClass = MountableItemService::class,
        beanMethod = "createMountableItem",
        operation = Operation(
            operationId = "createMountableItem",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = [Content(schema = Schema(implementation = MountableItemResponse::class))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid Mountable Item"),
                ApiResponse(responseCode = "404", description = "Mountable step not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "mountableStep")],
            requestBody = RequestBody(content = [Content(schema = Schema(implementation = MountableItemRequest::class))])
        )
    ),
    RouterOperation(
        path = "/v1/products/steps/{mountableStepId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.PUT],
        beanClass = MountableStepService::class,
        beanMethod = "updateMountableStep",
        operation = Operation(
            operationId = "updateMountableStep",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = [Content(schema = Schema(implementation = MountableStepResponse::class))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid Mountable Step Id"),
                ApiResponse(responseCode = "404", description = "Mountable Step not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "mountableStepId")],
            requestBody = RequestBody(content = [Content(schema = Schema(implementation = MountableStepRequest::class))])
        )
    ),
    RouterOperation(
        path = "/v1/products/steps/items/{mountableItemId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.PUT],
        beanClass = MountableItemService::class,
        beanMethod = "updateMountableItem",
        operation = Operation(
            operationId = "updateMountableItem",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = [Content(schema = Schema(implementation = MountableItemResponse::class))]
                ),
                ApiResponse(responseCode = "400", description = "Invalid Mountable Item Id"),
                ApiResponse(responseCode = "404", description = "Mountable Item not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "mountableItemId")],
            requestBody = RequestBody(content = [Content(schema = Schema(implementation = MountableItemRequest::class))])
        )
    ),
    RouterOperation(
        path = "/v1/products/steps/{mountableStepId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.DELETE],
        beanClass = MountableStepService::class,
        beanMethod = "deleteMountableStepById",
        operation = Operation(
            operationId = "deleteMountableStepById",
            responses = [
                ApiResponse(
                    responseCode = "201",
                    description = "successful operation",
                ),
                ApiResponse(responseCode = "400", description = "Invalid Mountable Step Id"),
                ApiResponse(responseCode = "404", description = "Mountable Step not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "mountableStepId")],
            requestBody = RequestBody(content = [Content()])
        )
    ),
    RouterOperation(
        path = "/v1/products/steps/items/{mountableItemId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.DELETE],
        beanClass = MountableItemService::class,
        beanMethod = "deleteMountableItemById",
        operation = Operation(
            operationId = "deleteMountableItemById",
            responses = [
                ApiResponse(
                    responseCode = "201",
                    description = "successful operation",
                ),
                ApiResponse(responseCode = "400", description = "Invalid Mountable Item Id"),
                ApiResponse(responseCode = "404", description = "Mountable Item not found")
            ],
            parameters = [Parameter(`in` = ParameterIn.PATH, name = "mountableItemId")],
            requestBody = RequestBody(content = [Content()])
        )
    )
)
annotation class MountableProductsDoc