package com.ewersson.dashboard_bi_api.model.sales

@JvmRecord
data class SalesDTO(
    val productName: String? = null,
    val productPrice: Double? = 0.0,
    val productImage: String? = null,
    val saleState: String? = null,
    val quantity: Int? = 1
) {
    companion object {
        fun fromEntity(sale: Sales): SalesDTO {
            return SalesDTO(
                productName = sale.productName,
                productPrice = sale.productPrice,
                productImage = sale.productImage,
                saleState = sale.state,
                quantity = sale.quantity
            )
        }
    }
}


