package com.ewersson.dashboard_bi_api.model.users

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
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

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole? = null,

    @Column(name = "login", nullable = false)
    var login: String? = null,

    @Column(name = "password", nullable = false)
    private val password: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonBackReference
    var dashboards: MutableList<Dashboard>? = mutableListOf()

): UserDetails {



    fun getUserLogin(): String? {
        return login
    }

    constructor(role: UserRole, login: String, password: String, dashboards: MutableList<Dashboard>?) : this(null, role, login, password, null)

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