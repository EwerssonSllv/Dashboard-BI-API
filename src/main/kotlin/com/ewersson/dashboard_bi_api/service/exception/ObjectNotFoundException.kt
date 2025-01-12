package com.ewersson.dashboard_bi_api.service.exception

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.NOT_FOUND)
class ObjectNotFoundException(message: String?) : EntityNotFoundException(message)