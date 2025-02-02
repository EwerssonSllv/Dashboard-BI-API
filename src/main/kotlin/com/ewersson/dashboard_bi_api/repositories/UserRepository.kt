package com.ewersson.dashboard_bi_api.repositories

import com.ewersson.dashboard_bi_api.model.users.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {

    @EntityGraph(attributePaths = ["sales", "dashboards", "products"])
    fun findByLogin(login: String?): UserDetails?
}