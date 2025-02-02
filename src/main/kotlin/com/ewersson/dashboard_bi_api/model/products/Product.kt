package com.ewersson.dashboard_bi_api.model.products

import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.ewersson.dashboard_bi_api.model.users.User
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*


@Entity
@Table(name = "products")
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    var id: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "product-user")
    var user: User? = null,

    @Column(name="name", nullable = false)
    var name: String,

    @Column(name = "image", nullable = false)
    var image: String,

    @Column(name = "price", nullable = false)
    var price: Double,

    @Column(name = "stock", nullable = false)
    var stock: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    @JsonBackReference(value = "product-sales")
    var sale: Sales? = null
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}