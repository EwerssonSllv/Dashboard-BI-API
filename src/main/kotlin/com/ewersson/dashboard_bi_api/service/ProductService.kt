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
    fun getProductByName(productName: String, authenticatedUser: User): List<ProductDTO>
    fun getProductsByUser(authenticatedUser: User): List<ProductDTO>

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

    fun findAllProducts(): List<ProductDTO> {
        val products = productRepository.findAll()
        return products.map { ProductDTO.fromEntity(it) }
    }

    fun findAllProductsByUser(user: User): List<ProductDTO> {
        val products = productRepository.findByUserId(user.id!!)
        return products.map { ProductDTO.fromEntity(it) }
    }

    override fun getProductByName(productName: String, authenticatedUser: User): List<ProductDTO> {
        val products = productRepository.findByNameContainingIgnoreCaseAndUser(productName, authenticatedUser)
        return products.sortedBy { it.name.length }
            .map { ProductDTO.fromEntity(it) }
    }

    override fun getProductsByUser(user: User): List<ProductDTO> {
        val products = productRepository.findByUserId(user.id!!)
        return products.map { ProductDTO.fromEntity(it) }
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
