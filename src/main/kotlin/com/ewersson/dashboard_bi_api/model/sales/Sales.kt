package com.ewersson.dashboard_bi_api.model.sales

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "sales")
data class Sales(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    private var id: String? = null,

    @Column(name = "state")
    var state: String,

    @Column(name = "sale")
    var sale: Double,

    @Column(name = "average")
    var average: Double,

    @Column(name = "amount")
    var amount: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id", nullable = false)
    @JsonBackReference
    var dashboard: Dashboard
) {

    fun getId(): String? = id

    override fun toString(): String {
        return "Sales: id: $id, state: '$state', sale: $sale, average: $average, amount: $amount, dashboard: $dashboard)"
    }

}