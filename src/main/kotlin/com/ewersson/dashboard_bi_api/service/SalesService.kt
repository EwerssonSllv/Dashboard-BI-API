package com.ewersson.dashboard_bi_api.service


import com.ewersson.dashboard_bi_api.model.products.Product
import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.ProductRepository
import com.ewersson.dashboard_bi_api.repositories.SalesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SalesService

    @Autowired
    constructor(

    private val salesRepository: SalesRepository,

    private val productRepository: ProductRepository
    ){

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

    fun findAllSales(): List<Sales> {
        return salesRepository.findAll()
    }

    fun getSaleById(id: String): SalesDTO? {
        val sale = salesRepository.findById(id)
        return if (sale.isPresent) {
            SalesDTO.fromEntity(sale.get())
        } else {
            null
        }
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

