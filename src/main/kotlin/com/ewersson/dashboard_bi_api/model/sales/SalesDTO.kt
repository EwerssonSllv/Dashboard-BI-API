package com.ewersson.dashboard_bi_api.model.sales

import com.ewersson.dashboard_bi_api.model.products.Product

data class SalesDTO(
    val id: String? = null,
    val state: String,
    val sale: Double,
    val average: Double,
    val amount: Double,
    val product: MutableList<Product>?,
    val dashboardId: String,
) {
    companion object {
        fun fromEntity(sale: Sales): SalesDTO {
            return SalesDTO(
                state = sale.state,
                sale = sale.sale,
                average = sale.average,
                amount = sale.amount,
                product = sale.products,
                dashboardId = sale.dashboard.id!!
            )
        }
    }
}


