package com.ewersson.dashboard_bi_api.repositories

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.products.Product
import com.ewersson.dashboard_bi_api.model.users.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, String> {
    fun findByUserId(userId: String): List<Product>
    fun findByNameContainingIgnoreCaseAndUser(name: String, user: User): List<Product>
}