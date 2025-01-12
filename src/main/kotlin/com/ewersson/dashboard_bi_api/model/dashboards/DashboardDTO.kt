package com.ewersson.dashboard_bi_api.model.dashboards

import com.ewersson.dashboard_bi_api.model.users.User
import java.time.LocalDateTime

data class DashboardDTO(
    val id: Int? = null,
    val name: String,
    val description: String,
    val user: User?
) {
    companion object {
        fun fromEntity(entity: Dashboard): DashboardDTO {
            return DashboardDTO(
                name = entity.name,
                description = entity.description,
                user = entity.user
            )
        }
    }
}
