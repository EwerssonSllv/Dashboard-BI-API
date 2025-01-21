package com.ewersson.dashboard_bi_api.model.products

import com.ewersson.dashboard_bi_api.model.users.User

@JvmRecord
data class ProductDTO(
    val id: String? = null,
    val user: User? = null,
    val name: String,
    val image: String,
    val price: Double,
    val stock: Int
) {
    companion object {
        fun fromEntity(product: Product): ProductDTO {
            return ProductDTO(
                id = product.id,
                user = product.user,
                name = product.name,
                image = product.image,
                price = product.price,
                stock = product.stock
            )
        }
    }
}
