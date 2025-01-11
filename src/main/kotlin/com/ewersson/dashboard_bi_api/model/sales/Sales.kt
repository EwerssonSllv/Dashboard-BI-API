package com.ewersson.dashboard_bi_api.model.sales

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "sales")
class Sales(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    var id: Int? = null,

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
    var dashboard: Dashboard? = null

) {

    constructor() : this(null, "", 0.0, 0.0, 0.0, null)

    fun calculateAmount() {
        this.amount = sale + average
    }

    override fun toString(): String {
        return "Sales: id: $id, state: '$state', sale: $sale, average: $average, amount: $amount, dashboard: $dashboard)"
    }

    fun getId(): Int? = id

    fun getState(): String = state

    fun getSale(): Double = sale

    fun getAverage(): Double = average

    fun getAmount(): Double = amount

    fun getDashboard(): Dashboard? = dashboard

    fun setId(id: Int){
        this.id = id
    }

    fun setState(newState: String){
        this.state = newState
    }

    fun setSale(newSale: Double){
        this.sale = newSale
    }

    fun setAverage(newAverage: Double){
        this.average = newAverage
    }

    fun setAmount(average: Double, amount: Double){
        this.amount = sale + average
    }

    fun setDashboard(dashboard: Dashboard?){
        this.dashboard = dashboard
    }

}