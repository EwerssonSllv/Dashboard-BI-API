package com.ewersson.dashboard_bi_api.service

import com.ewersson.dashboard_bi_api.model.dashboards.Dashboard
import com.ewersson.dashboard_bi_api.model.sales.Sales
import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.DashboardRepository
import com.ewersson.dashboard_bi_api.repositories.SalesRepository
import com.ewersson.dashboard_bi_api.service.exception.ObjectNotFoundException
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

    fun createSales(dashboardId: String, salesDTO: SalesDTO, authenticatedUser: User): SalesDTO {
        val dashboard = dashboardRepository.findById(dashboardId)
            .orElseThrow { ObjectNotFoundException("Dashboard not found!") }

        val sale = Sales(
            state = salesDTO.state,
            sale = salesDTO.sale,
            average = salesDTO.average,
            amount = salesDTO.amount,
            dashboard = dashboard
        )

        val savedSale = salesRepository.save(sale)

        return SalesDTO.fromEntity(savedSale)
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
