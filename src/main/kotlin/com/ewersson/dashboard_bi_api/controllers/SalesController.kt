package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.DashboardRepository
import com.ewersson.dashboard_bi_api.service.SalesService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("sales")
class SalesController(
    @Autowired
    private val salesService: SalesService,

    @Autowired
    private val dashboardRepository: DashboardRepository

) {

    @PostMapping("/save")
    fun createSales(
        @RequestBody @Valid salesDTO: SalesDTO,  // Aceita apenas um único item de SalesDTO
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<SalesDTO> {  // Retorna um único SalesDTO
        val dashboardId = salesDTO.dashboardId
        val sales = salesService.createSales(dashboardId, salesDTO, authenticatedUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(sales)
    }


    @GetMapping("/{id}")
    fun getSale(@PathVariable id: String): ResponseEntity<SalesDTO> {
        val sale = salesService.getSaleById(id)
        return if (sale != null) {
            ResponseEntity.ok(sale)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSale(@PathVariable id: String): ResponseEntity<Void> {
        return if (salesService.deleteSale(id)) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}
