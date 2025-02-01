package com.ewersson.dashboard_bi_api.repositories

import com.ewersson.dashboard_bi_api.model.sales.Sales
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SalesRepository : JpaRepository<Sales, String> {

    fun findByDate(date: LocalDate): List<Sales>

    fun findByProductNameContainingIgnoreCase(productName: String): List<Sales>

}

