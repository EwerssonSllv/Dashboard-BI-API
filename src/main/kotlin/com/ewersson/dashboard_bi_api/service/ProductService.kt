package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.products.Product
import com.ewersson.dashboard_bi_api.model.products.ProductDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.ProductRepository
import com.ewersson.dashboard_bi_api.repositories.UserRepository
import com.ewersson.dashboard_bi_api.service.exception.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface ProductService {
    fun createProduct(productDTO: ProductDTO, authenticatedUser: User): ProductDTO
    fun getProductById(productId: String, authenticatedUser: User): ProductDTO
}

@Service
class ProductServiceImpl(

    @Autowired
    private val productRepository: ProductRepository,
    @Autowired
    private val userRepository: UserRepository

): ProductService {

    override fun createProduct(productDTO: ProductDTO, authenticatedUser: User): ProductDTO {
        val user = userRepository.findById(authenticatedUser.id!!)
            .orElseThrow { IllegalArgumentException("User not found!") }
        val product = Product(
            name = productDTO.name,
            image = productDTO.image,
            price = productDTO.price,
            stock = productDTO.stock,
            sale = null,
            user = user
        )
        val savedProduct = productRepository.save(product)
        return ProductDTO.fromEntity(savedProduct)
    }

    override fun getProductById(productId: String, authenticatedUser: User): ProductDTO {
        val product = productRepository.findByIdAndUser(productId, authenticatedUser)
            ?: throw IllegalArgumentException("Product not found!")
        return ProductDTO.fromEntity(product)
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

    fun updateStock(productId: String, quantity: Int): ProductDTO {
        val product = productRepository.findById(productId)
            .orElseThrow { ObjectNotFoundException("Product not found!") }
        product.stock = quantity
        val updatedProduct = productRepository.save(product)
        return ProductDTO.fromEntity(updatedProduct)
    }

}
