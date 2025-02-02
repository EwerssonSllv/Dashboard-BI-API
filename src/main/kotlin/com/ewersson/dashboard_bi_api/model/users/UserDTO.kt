package com.ewersson.dashboard_bi_api.model.users

import com.ewersson.dashboard_bi_api.model.dashboards.DashboardDTO
import com.ewersson.dashboard_bi_api.model.products.ProductDTO
import com.ewersson.dashboard_bi_api.model.sales.SalesDTO

data class UserDTO(
    val id: String,
    val login: String,
    val role: UserRole,
    val dashboards: List<DashboardDTO>? = null,
    val sales: List<SalesDTO>? = null,
    val products: List<ProductDTO>? = null
) {
    companion object {
        fun fromEntity(
            user: User,
            dashboards: List<DashboardDTO>,
            sales: List<SalesDTO>,
            products: List<ProductDTO>
        ) = UserDTO(
            id = user.id!!,
            login = user.login,
            role = user.role,
            dashboards = dashboards,
            sales = sales,
            products = products
        )
    }
}

