package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.model.nlp.CommandRequest
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.service.CommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/nlp")
class NLPController(
    @Autowired
    private val commandService: CommandService
) {

    @PostMapping("/query")
    fun query(
        @RequestBody commandRequest: CommandRequest,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<Any> {
        return commandService.processCommand(commandRequest.command, authenticatedUser)
    }

}