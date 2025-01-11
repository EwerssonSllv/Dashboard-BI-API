package com.ewersson.dashboard_bi_api.model.dashboards

import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.ewersson.dashboard_bi_api.model.users.User
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "dashboards")
class Dashboard(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    var id: Int? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @OneToMany(mappedBy = "dashboard", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    var sales: MutableList<Sales> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

) {
    constructor() : this(null, "", "", mutableListOf(), null)

    fun getId(): Int? = id
    fun getName(): String = name
    fun getDescription(): String = description
    fun getSales(): MutableList<Sales> = sales
    fun getUser(): User? = user

    // Setters
    fun setId(newId: Int?) {
        id = newId
    }

    fun setName(newName: String) {
        name = newName
    }

    fun setDescription(newDescription: String) {
        description = newDescription
    }

    fun setSales(newSales: MutableList<Sales>) {
        sales = newSales
    }

    fun setUser(newUser: User?) {
        user = newUser
    }
}