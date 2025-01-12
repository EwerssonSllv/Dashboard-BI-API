package com.ewersson.dashboard_bi_api.repositories

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.sales.Sales
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SalesRepository : JpaRepository<Sales, String> {
    fun findAllByDashboard(dashboard: Dashboard): List<Sales>
}

