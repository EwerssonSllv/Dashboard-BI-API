package com.ewersson.dashboard_bi_api.model.sales

@JvmRecord
data class SalesByStateDTO(
    val state: String,
    val totalQuantity: Int,
    val totalValue: Double,
    val averagePrice: Double
)
