package com.finapp.api.products.mapper

import com.finapp.api.products.data.Product
import com.finapp.api.products.model.*
import com.finapp.api.products.model.ProductType.Companion.toProductType
import org.springframework.stereotype.Component

@Component
class ProductMapper {
    fun productRequestToProduct(productRequest: ProductRequest): Product = Product(
        name = productRequest.name!!,
        price = productRequest.price,
        isActive = productRequest.isActive!!,
        category = productRequest.category!!,
        type = productRequest.type!!.toProductType()
    )

    fun productRequestToProduct(productRequest: ProductRequest?, product: Product): Product = product.copy(
        name = productRequest?.name ?: product.name,
        price = productRequest?.getProductPrice(product),
        isActive = productRequest?.isActive ?: product.isActive,
        category = productRequest?.category ?: product.category,
        type = productRequest?.type?.toProductType() ?: product.type,
    )

    fun productToProductResponse(product: Product): ProductResponse = ProductResponse(
        id = product.id?.toHexString(),
        name = product.name,
        price = product.price,
        isActive = product.isActive,
        imageId = product.imageId?.toHexString(),
        category = product.category,
        type = product.type.name
    )

    private fun ProductRequest?.getProductPrice(product: Product) =
        if (this?.isVariable == true) { null }
        else { this?.price ?: product.price }
}
