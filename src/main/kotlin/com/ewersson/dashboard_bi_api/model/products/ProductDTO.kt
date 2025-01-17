package com.ewersson.dashboard_bi_api.model.products

data class ProductDTO(
    val id: String? = null,
    val name: String,
    val image: String,
    val price: Double,
    val stock: Int,
    val saleId: String
) {

    companion object {
        fun fromEntity(product: Product): ProductDTO {
            return ProductDTO(
                name = product.name,
                image = product.image,
                price = product.price,
                stock = product.stock,
                saleId = product.sale.id!!
            )
        }
    }
}