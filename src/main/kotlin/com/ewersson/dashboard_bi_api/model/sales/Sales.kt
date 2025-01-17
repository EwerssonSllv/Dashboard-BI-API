package com.ewersson.dashboard_bi_api.model.sales

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.products.Product
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "sales")
data class Sales(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    var id: String? = null,

    @Column(name = "state")
    var state: String,

    @Column(name = "sale")
    var sale: Double,

    @Column(name = "average")
    var average: Double,

    @Column(name = "amount")
    var amount: Double,

    @OneToMany(mappedBy = "sale", cascade = [CascadeType.ALL], orphanRemoval = true)
    var products: MutableList<Product>? = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id", nullable = false)
    @JsonBackReference
    var dashboard: Dashboard
)