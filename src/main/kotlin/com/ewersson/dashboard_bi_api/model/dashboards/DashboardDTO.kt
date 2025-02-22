package com.ewersson.dashboard_bi_api.model.dashboards

import com.ewersson.dashboard_bi_api.model.sales.Sales

@JvmRecord
data class DashboardDTO(
    val id: String? = null,
    val name: String,
    val description: String,
    val sale: List<Sales>?,
    val userId: String? = null
) {
    companion object {
        fun fromEntity(dashboard: Dashboard): DashboardDTO {
            return DashboardDTO(
                id = dashboard.id!!,
                name = dashboard.name,
                description = dashboard.description,
                sale = dashboard.sales,
                userId = dashboard.user?.id!!
            )
        }
    }
}

