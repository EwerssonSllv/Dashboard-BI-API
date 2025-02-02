package com.ewersson.dashboard_bi_api.repositories

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.users.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DashboardRepository : JpaRepository<Dashboard, String> {
    fun findByIdAndUser(id: String, user: User): List<Dashboard>?
    fun findByUserId(userId: String): List<Dashboard>

}



