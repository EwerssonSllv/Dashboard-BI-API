package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.model.products.ProductDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.service.ProductService
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
    private val productService: ProductService
) {

    @PostMapping
    fun createProduct(
        @RequestBody @Valid productDTO: ProductDTO,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<ProductDTO> {
        val createdProduct = productService.createProduct(productDTO, authenticatedUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)
    }

}