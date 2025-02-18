package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.products.Product
import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.SalesRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SalesService(
    private val salesRepository: SalesRepository
) {

    fun createSale(saleDTO: SalesDTO, authenticatedUser: User, product: Product): Sales {
        if (product.stock < saleDTO.quantity!!) {
            throw IllegalStateException("Not enough stock for the product")
        }

        product.stock -= saleDTO.quantity

        val sale = Sales(
            state = authenticatedUser.state,
            productName = product.name,
            productPrice = product.price,
            productImage = product.image,
            dashboard = null,
            date = LocalDateTime.now(),
            quantity = saleDTO.quantity,
            user = authenticatedUser
        )

        return salesRepository.save(sale)
    }

    fun getSalesByUser(authenticatedUser: User): List<SalesDTO> {
        val sales = salesRepository.findByUserId(authenticatedUser.id!!)
        return sales.map { SalesDTO.fromEntity(it) }
    }


    fun findAllSales(): List<Sales> {
        return salesRepository.findAll()
    }

    fun deleteSale(id: String): Boolean {
        return if (salesRepository.existsById(id)) {
            salesRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}

