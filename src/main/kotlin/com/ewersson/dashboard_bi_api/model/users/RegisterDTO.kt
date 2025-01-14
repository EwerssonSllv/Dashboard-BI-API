package com.ewersson.dashboard_bi_api.model.users

@JvmRecord
data class RegisterDTO(
    val login: String,
    val password: String,
    val role: UserRole? = null)