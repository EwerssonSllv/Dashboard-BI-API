package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(

    @Autowired
    private val userRepository: UserRepository

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