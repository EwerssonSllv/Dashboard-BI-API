package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.DashboardRepository
import com.ewersson.dashboard_bi_api.repositories.SalesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SalesService(

    @Autowired
    private val salesRepository: SalesRepository,
    @Autowired
    private val dashboardRepository: DashboardRepository
) {

    fun createSales(dashboardId: String, salesDTO: List<SalesDTO>, authenticatedUser: User): List<SalesDTO> {
        val dashboard = dashboardRepository.findByIdAndUser(dashboardId, authenticatedUser)
            ?: throw IllegalArgumentException("Dashboard not found!")
        val sales = salesDTO.map {
            Sales(
                state = it.state,
                sale = it.sale,
                average = it.average,
                amount = it.amount,
                dashboard = dashboard
            )
        }
        return salesRepository.saveAll(sales).map { SalesDTO.fromEntity(it) }
    }




    fun getSaleById(id: String): SalesDTO? {
        val sale = salesRepository.findById(id).orElse(null)
        return sale?.let { SalesDTO.fromEntity(it) }
    }

    fun deleteSale(id: String): Boolean {
        return if (salesRepository.existsById(id)) {
            salesRepository.deleteById(id)
            true
        } else {
            false
        }
    }

}
