package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.model.products.ProductDTO
import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.service.ProductService
import com.ewersson.dashboard_bi_api.service.ProductServiceImpl
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    @Autowired
    private val productService: ProductServiceImpl
) {

    @PostMapping
    fun createProduct(
        @RequestBody @Valid productDTO: ProductDTO,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<ProductDTO> {
        val createdProduct = productService.createProduct(productDTO, authenticatedUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)
    }

    @GetMapping("/{id}")
    fun getProduct(
        @PathVariable id: String,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<ProductDTO> {
        val product = productService.getProductById(id)
        return product?.let { ResponseEntity.ok(it) } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: String,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<Void> {
        return if (productService.deleteProduct(id)) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

}