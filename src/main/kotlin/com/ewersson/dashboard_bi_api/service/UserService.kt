package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.dashboards.DashboardDTO
import com.ewersson.dashboard_bi_api.model.products.ProductDTO
import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.DashboardRepository
import com.ewersson.dashboard_bi_api.repositories.ProductRepository
import com.ewersson.dashboard_bi_api.repositories.SalesRepository
import com.ewersson.dashboard_bi_api.repositories.UserRepository
import com.ewersson.dashboard_bi_api.service.exception.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(

    @Autowired
    private val userRepository: UserRepository,

    @Autowired
    private val dashboardRepository: DashboardRepository,

    @Autowired
    private val productRepository: ProductRepository,

    @Autowired
    private val salesRepository: SalesRepository

): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
    }

    fun getAuthenticatedUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication == null || authentication.principal !is User) {
            throw IllegalStateException("No authenticated user found")
        }

        return authentication.principal as User
    }



    fun getDashboardsByUser(user: User): List<DashboardDTO> {
        val dashboards = dashboardRepository.findByUserId(user.id!!)

        if(dashboards.isEmpty()){
            throw ObjectNotFoundException("Dashboards not found!")
        }

        return dashboards.map { DashboardDTO.fromEntity(it) }
    }

    fun getProductsByUser(user: User): List<ProductDTO> {
        val products = productRepository.findByUserId(user.id!!)

        if(products.isEmpty()){
            throw ObjectNotFoundException("Products not found!")
        }

        return products.map { ProductDTO.fromEntity(it) }
    }

    fun getSalesByUser(user: User): List<SalesDTO> {
        val sales = salesRepository.findByUserId(user.id!!)

        if(sales.isEmpty()){
            throw ObjectNotFoundException("Sales not found!")
        }

        return sales.map { SalesDTO.fromEntity(it) }

    }

    fun getUserByLogin(login: String): UserDetails {
        return userRepository.findByLogin(login)
            ?: throw UsernameNotFoundException("Usuário não encontrado")
    }

    fun findById(id: String): User {
        return userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User not found with ID: $id") }
    }

    fun deleteUser(id: String) {
        if (!userRepository.existsById(id)) {
            throw IllegalArgumentException("User not found with ID: $id")
        }
        userRepository.deleteById(id)
    }
}