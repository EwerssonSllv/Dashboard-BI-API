package com.ewersson.dashboard_bi_api.model.users

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.products.Product
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import lombok.Getter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
@Getter
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    var id: String? = null,

    @Column(name = "state", nullable = false)
    var state: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole,

    @Column(name = "login", nullable = false)
    var login: String,

    @Column(name = "password", nullable = false)
    private val password: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-dashboards")
    var dashboards: MutableList<Dashboard>? = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-products")
    var products: MutableList<Product>? = mutableListOf()

): UserDetails {

    fun getUserLogin(): String? {
        return login
    }

    constructor(state: String, role: UserRole, login: String, password: String, dashboards: MutableList<Dashboard>?, products: MutableList<Product>?) : this(null, state, role, login, password, null, null)

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return if (role == UserRole.ADMIN) {
            listOf(
                SimpleGrantedAuthority("ROLE_ADMIN"),
                SimpleGrantedAuthority("ROLE_USER")
            )
        } else {
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        }
    }

    override fun getPassword(): String = password

    override fun getUsername(): String? = login

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}