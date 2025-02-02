package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.dashboards.DashboardDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.DashboardRepository
import com.ewersson.dashboard_bi_api.repositories.UserRepository

import org.springframework.stereotype.Service

interface DashboardService {
    fun createDashboard(dashboardDTO: DashboardDTO, authenticatedUser: User): DashboardDTO
}

@Service
class DashboardServiceImpl(
    private val dashboardRepository: DashboardRepository,
    private val userRepository: UserRepository
) : DashboardService {

    override fun createDashboard(dashboardDTO: DashboardDTO, authenticatedUser: User): DashboardDTO {
        val user = userRepository.findById(authenticatedUser.id!!)
            .orElseThrow { IllegalArgumentException("User not found!") }
        val dashboard = Dashboard(
            name = dashboardDTO.name,
            description = dashboardDTO.description,
            user = user
        )
        val savedDashboard = dashboardRepository.save(dashboard)
        return DashboardDTO.fromEntity(savedDashboard)
    }

    fun deleteDashboard(id: String): Boolean {
        return if (dashboardRepository.existsById(id)) {
            dashboardRepository.deleteById(id)
            true
        } else {
            false
        }
    }

}

