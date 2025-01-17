package com.ewersson.dashboard_bi_api.model.products

import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*


@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    var id: String? = null,

    @Column(name="name", nullable = false)
    var name: String,

    @Column(name = "image", nullable = false)
    var image: String,

    @Column(name = "price", nullable = false)
    var price: Double,

    @Column(name = "stock", nullable = false)
    var stock: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_id", nullable = false)
    @JsonBackReference
    var sale: Sales
)