package com.ewersson.dashboard_bi_api.repositories

import com.ewersson.dashboard_bi_api.model.products.Product
import com.ewersson.dashboard_bi_api.model.users.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, String> {
    fun findByIdAndUser(id: String, user: User): Product?
    fun findByUserId(userId: String): List<Product>
}