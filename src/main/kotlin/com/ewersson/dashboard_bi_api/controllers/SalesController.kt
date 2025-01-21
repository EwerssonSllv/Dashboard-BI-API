package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.ProductRepository
import com.ewersson.dashboard_bi_api.service.SalesService
import com.ewersson.dashboard_bi_api.service.exception.ObjectNotFoundException
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sales")
class SalesController(
    @Autowired
    private val salesService: SalesService,

    @Autowired
    private val productRepository: ProductRepository
) {

    @PostMapping("/{productId}")
    fun createSales(
        @RequestBody @Valid salesDTO: SalesDTO,
        @PathVariable productId: String,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<SalesDTO> {
        val product = productRepository.findById(productId)
            .orElseThrow { ObjectNotFoundException("Product not found with ID: $productId") }


        val sale = salesService.createSale(salesDTO, authenticatedUser, product)

        val saleDTOResponse = SalesDTO.fromEntity(sale)

        return ResponseEntity.status(HttpStatus.CREATED).body(saleDTOResponse)
    }

    @GetMapping("/{id}")
    fun getSale(@PathVariable id: String): ResponseEntity<SalesDTO> {
        val sale = salesService.getSaleById(id)
        return sale?.let { ResponseEntity.ok(it) } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).build()
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

