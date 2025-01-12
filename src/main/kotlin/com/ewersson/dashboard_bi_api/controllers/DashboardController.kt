package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.model.dashboards.DashboardDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.service.DashboardService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

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
