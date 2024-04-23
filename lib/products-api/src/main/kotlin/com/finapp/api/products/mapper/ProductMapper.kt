package com.finapp.api.products.mapper

import com.finapp.api.products.data.Product
import com.finapp.api.products.model.*
import com.finapp.api.products.model.ProductType.Companion.toProductType
import com.finapp.api.mountable_products.mapper.MountableProductMapper
import org.springframework.stereotype.Component

@Component
class ProductMapper(
    private val mountableProductMapper: MountableProductMapper
) {
    fun productRequestToProduct(productRequest: ProductRequest): Product = Product(
        name = productRequest.name!!,
        price = productRequest.price,
        isActive = productRequest.isActive!!,
        category = productRequest.category!!,
        type = productRequest.type!!.toProductType(),
        steps = productRequest.steps?.map { mountableProductMapper.mountableStepRequestToMountableStep(it) }?.toMutableList()
    )

    fun productRequestToProduct(productRequest: ProductRequest?, product: Product): Product = product.copy(
        name = productRequest?.name ?: product.name,
        price = productRequest?.getProductPrice(product),
        isActive = productRequest?.isActive ?: product.isActive,
        category = productRequest?.category ?: product.category,
        type = productRequest?.type?.toProductType() ?: product.type,
        steps = with(product.steps) {
            productRequest?.steps?.forEach {
                val newItem = mountableProductMapper.mountableStepRequestToMountableStep(it, this!![it.index])
                this.removeAt(it.index)
                this.add(it.index, newItem)
            }

            return@with this
        }
    )

    fun productToProductResponse(product: Product): ProductResponse = ProductResponse(
        id = product.id?.toHexString(),
        name = product.name,
        price = product.price,
        isActive = product.isActive,
        category = product.category,
        type = product.type.name,
        steps = product.steps?.map { mountableProductMapper.mountableStepToMountableStepResponse(it) }
    )

    private fun ProductRequest?.getProductPrice(product: Product) =
        if (this?.isVariable == true) { null }
        else { this?.price ?: product.price }
}
