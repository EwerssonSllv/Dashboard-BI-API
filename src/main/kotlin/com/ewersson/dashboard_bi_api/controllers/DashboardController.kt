package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.model.dashboards.DashboardDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.service.DashboardServiceImpl
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboards")
class DashboardController(
    @Autowired
    private val dashboardService: DashboardServiceImpl
) {

    @PostMapping
    fun createDashboard(
        @RequestBody @Valid dashboardDTO: DashboardDTO,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<DashboardDTO> {
        val createdDashboard = dashboardService.createDashboard(dashboardDTO, authenticatedUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDashboard)
    }

    @GetMapping("/user")
    fun getDashboardsByUser(
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<List<DashboardDTO>> {
        val dashboards = dashboardService.getDashboardsByUser(authenticatedUser)
        return ResponseEntity.ok(dashboards)
    }

    @DeleteMapping("/{id}")
    fun deleteDashboard(
        @PathVariable id: String,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<Void> {
        return if (dashboardService.deleteDashboard(id)) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

}

