package com.ewersson.dashboard_bi_api.model.users

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.products.Product
import com.ewersson.dashboard_bi_api.model.sales.Sales

@JvmRecord
data class RegisterDTO(
    val login: String,
    val password: String,
    val state: String
)
