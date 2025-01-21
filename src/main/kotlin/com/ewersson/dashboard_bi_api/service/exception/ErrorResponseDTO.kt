package com.ewersson.dashboard_bi_api.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
data class ErrorResponseDTO(val message: String)
