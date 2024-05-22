package com.finapp.api.products.service

import com.finapp.api.core.validation.*
import com.finapp.api.products.model.ProductArg
import com.finapp.api.products.model.ProductParam
import com.finapp.api.products.model.ProductRequest
import com.finapp.api.products.model.ProductResponse
import jakarta.validation.Valid
import org.springframework.http.codec.multipart.Part
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
interface ProductService {
    @Validated(OnRead::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getProductById(@Valid productParam: ProductParam): Mono<ProductResponse>
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_READ')")
    fun getAllProducts(isFull: Boolean): Flux<ProductResponse>
    @Validated(OnUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun completeUpdateProduct(@Valid productArg: ProductArg): Mono<ProductResponse>
    @Validated(OnPartialUpdate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun partialUpdateProduct(@Valid productArg: ProductArg): Mono<ProductResponse>
    @Validated(OnCreate::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_WRITE')")
    fun createProduct(@Valid productRequest: ProductRequest): Mono<ProductResponse>
    @Validated(OnDelete::class)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_DELETE')")
    fun deleteProduct(@Valid productParam: ProductParam?): Mono<Boolean>
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_UPDATE')")
    fun updateProductImage(get: List<Part>?, productParam: ProductParam): Mono<ProductResponse>
}