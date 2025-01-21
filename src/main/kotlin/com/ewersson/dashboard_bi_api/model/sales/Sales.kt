package com.ewersson.dashboard_bi_api.model.sales

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "sales")
data class Sales(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    var id: String? = null,

    @CreationTimestamp
    @Column(name = "date_created", updatable = false, nullable = false)
    var date: LocalDateTime = LocalDateTime.now(),

    @Column(name = "product_name")
    var productName: String? = null,

    @Column(name = "product_price")
    var productPrice: Double? = null,

    @Column(name = "product_image")
    var productImage: String? = null,

    @Column(name = "quantity")
    var quantity: Int? = null,

    @Column(name = "state")
    var state: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id", nullable = false)
    @JsonBackReference
    var dashboard: Dashboard? = null
)
