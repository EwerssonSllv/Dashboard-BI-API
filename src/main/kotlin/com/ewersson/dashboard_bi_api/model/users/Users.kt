package com.ewersson.dashboard_bi_api.model.users

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    var id: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var dashboards: MutableList<Dashboard> = mutableListOf()

) {
    constructor() : this(null, null, "", "", mutableListOf())

    fun getId(): Int? = id
    fun getName(): String = name
    fun getPassword(): String = password
    fun getDashboards(): MutableList<Dashboard> = dashboards

    // Setters
    fun setId(newId: Int?) {
        id = newId
    }

    fun setName(newName: String) {
        name = newName
    }

    fun setPassword(newPassword: String) {
        password = newPassword
    }

    fun setDashboards(newDashboards: MutableList<Dashboard>) {
        dashboards = newDashboards
    }
}