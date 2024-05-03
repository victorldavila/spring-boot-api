package com.finapp.api.mountable_products

import com.finapp.api.core.error.BaseHandler
import com.finapp.api.core.error.ValidationHandler
import com.finapp.api.core.tryGetPathVariable
import com.finapp.api.mountable_products.model.*
import com.finapp.api.mountable_products.service.MountableItemService
import com.finapp.api.mountable_products.service.MountableStepService
import com.finapp.api.products.model.ProductResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class MountableProductHandler(
    private val mountableStepService: MountableStepService,
    private val mountableItemService: MountableItemService,
    validationHandler: ValidationHandler
): BaseHandler(validationHandler) {

    fun getMountableStepById(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getMountableStepParam(it) }
            .flatMap { mountableStepService.getMountableStepById(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun getAllMountableSteps(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getMountableStepParam(it) }
            .flatMap { ServerResponse.ok().body(mountableStepService.getMountableStepByProductId(it), ProductResponse::class.java) }
            .onErrorResume { errorResponse(it) }

    fun completeUpdateMountableStep(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateMountableStepArg(serverRequest)
            .flatMap { mountableStepService.completeUpdateMountableStep(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun partialUpdateMountableStep(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateMountableStepArg(serverRequest)
            .flatMap { mountableStepService.partialUpdateMountableStep(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    private fun updateMountableStepArg(serverRequest: ServerRequest): Mono<MountableStepArg> =
        Mono.just(serverRequest)
            .flatMap { serverRequest ->
                serverRequest.bodyToMono(MountableStepRequest::class.java)
                    .map { MountableStepArg(getMountableStepParam(serverRequest), it) }
            }

    fun createMountableStep(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { serverRequest ->
                serverRequest.bodyToMono(MountableStepRequest::class.java)
                    .map { MountableStepArg(getMountableStepParam(serverRequest), it) }
            }
            .flatMap { mountableStepService.createMountableStep(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun deleteMountableStep(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getMountableStepParam(it) }
            .flatMap { mountableStepService.deleteMountableStepById(it) }
            .flatMap { ServerResponse.noContent().build() }
            .onErrorResume { errorResponse(it) }

    fun getMountableItemById(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getMountableItemParam(it) }
            .flatMap { mountableItemService.getMountableItemsById(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun getAllMountableItems(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getMountableItemParam(it) }
            .flatMap { ServerResponse.ok().body(mountableItemService.getMountableItemsByMountableStepId(it), ProductResponse::class.java) }


    fun completeUpdateMountableItem(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateMountableItemArg(serverRequest)
            .flatMap { mountableItemService.completeUpdateMountableItem(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun partialUpdateMountableItem(serverRequest: ServerRequest): Mono<ServerResponse> =
        updateMountableItemArg(serverRequest)
            .flatMap { mountableItemService.partialUpdateMountableItem(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    private fun updateMountableItemArg(serverRequest: ServerRequest): Mono<MountableItemArg> =
        Mono.just(serverRequest)
            .flatMap { serverRequest ->
                serverRequest.bodyToMono(MountableItemRequest::class.java)
                    .map { MountableItemArg(getMountableItemParam(serverRequest), it) }
            }

    fun createMountableItem(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .flatMap { serverRequest ->
                serverRequest.bodyToMono(MountableItemRequest::class.java)
                    .map { MountableItemArg(getMountableItemParam(serverRequest), it) }
            }
            .flatMap { mountableItemService.createMountableItem(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .onErrorResume { errorResponse(it) }

    fun deleteMountableItem(serverRequest: ServerRequest): Mono<ServerResponse> =
        Mono.just(serverRequest)
            .map { getMountableItemParam(it) }
            .flatMap { mountableItemService.deleteMountableItemById(it) }
            .flatMap { ServerResponse.noContent().build() }
            .onErrorResume { errorResponse(it) }

    private fun getMountableStepParam(serverRequest: ServerRequest): MountableStepParam {
        val productId = serverRequest.tryGetPathVariable("productId")
        val mountableStepId = serverRequest.tryGetPathVariable("mountableStepId")

        return MountableStepParam(productId, mountableStepId)
    }

    private fun getMountableItemParam(serverRequest: ServerRequest): MountableItemParam {
        val mountableStepId = serverRequest.tryGetPathVariable("mountableStepId")
        val mountableItemId = serverRequest.tryGetPathVariable("mountableItemId")

        return MountableItemParam(mountableStepId, mountableItemId)
    }
}