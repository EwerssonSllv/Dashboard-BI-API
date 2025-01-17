package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.products.Product
import com.ewersson.dashboard_bi_api.model.products.ProductDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.ProductRepository
import com.ewersson.dashboard_bi_api.repositories.SalesRepository
import com.ewersson.dashboard_bi_api.service.exception.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService(
    @Autowired
    private val productRepository: ProductRepository,

    @Autowired
    private val salesRepository: SalesRepository
) {

    fun createProduct(saleId: String, productDTO: ProductDTO, authenticatedUser: User): ProductDTO {
        val sales = salesRepository.findById(saleId)
            .orElseThrow { ObjectNotFoundException("Sales not found!") }

        val product = Product(
            name = productDTO.name,
            image = productDTO.image,
            price = productDTO.price,
            stock = productDTO.stock,
            sale = sales
        )

        val savedProduct = productRepository.save(product)

        return ProductDTO.fromEntity(savedProduct)
    }

    fun getProductById(id: String): ProductDTO? {
        val product = productRepository.findById(id).orElse(null)
        return product?.let { ProductDTO.fromEntity(it) }
    }

    fun deleteProduct(id: String): Boolean {
        return if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
            true
        } else {
            false
        }
    }

}