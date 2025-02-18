package com.ewersson.dashboard_bi_api.model.sales

import com.ewersson.dashboard_bi_api.model.users.User
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

@JvmRecord
data class SalesDTO(
    val productName: String? = null,
    val productPrice: Double? = 0.0,
    val productImage: String? = null,
    val saleState: String? = null,
    val quantity: Int? = 1,
    @JsonIgnore
    val user: User? = null,
    val date: LocalDateTime? = null
) {
    companion object {
        fun fromEntity(sale: Sales): SalesDTO {
            return SalesDTO(
                productName = sale.productName,
                productPrice = sale.productPrice,
                productImage = sale.productImage,
                saleState = sale.state,
                quantity = sale.quantity,
                user = sale.user,
                date = sale.date
            )
        }
    }
}


