package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.dashboards.DashboardDTO
import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.DashboardRepository
import com.ewersson.dashboard_bi_api.repositories.SalesRepository
import com.ewersson.dashboard_bi_api.service.DashboardService
import com.ewersson.dashboard_bi_api.service.UserService
import com.ewersson.dashboard_bi_api.service.exception.ObjectNotFoundException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/dashboards")
class DashboardController(
    private val dashboardService: DashboardService
) {

    @PostMapping
    fun createDashboard(
        @RequestBody @Valid dashboardDTO: DashboardDTO,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<DashboardDTO> {
        val createdDashboard = dashboardService.createDashboard(dashboardDTO, authenticatedUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDashboard)
    }

    @GetMapping("/{id}")
    fun getDashboardById(
        @PathVariable id: String,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<DashboardDTO> {
        val dashboard = dashboardService.getDashboardById(id, authenticatedUser)
        return ResponseEntity.ok(dashboard)
    }
}
