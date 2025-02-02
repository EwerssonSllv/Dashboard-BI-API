package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.model.dashboards.DashboardDTO
import com.ewersson.dashboard_bi_api.model.nlp.CommandRequest
import com.ewersson.dashboard_bi_api.model.products.ProductDTO
import com.ewersson.dashboard_bi_api.model.sales.SalesDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.model.users.UserDTO
import com.ewersson.dashboard_bi_api.service.CommandService
import com.ewersson.dashboard_bi_api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("nlp")
class NLPController(
    @Autowired
    private val commandService: CommandService,

    @Autowired
    private val userService: UserService
) {

    @PostMapping("/query")
    fun query(
        @RequestBody commandRequest: CommandRequest,
        @AuthenticationPrincipal authenticatedUser: User
    ): ResponseEntity<Any> {
        return commandService.processCommand(commandRequest.command, authenticatedUser)
    }

    @GetMapping("/user")
    fun getUserData(@AuthenticationPrincipal authenticatedUser: User): ResponseEntity<UserDTO> {
        val dashboards = authenticatedUser.dashboards?.map { DashboardDTO.fromEntity(it) } ?: emptyList()
        val sales = authenticatedUser.sales?.map { SalesDTO.fromEntity(it) } ?: emptyList()
        val products = authenticatedUser.products?.map { ProductDTO.fromEntity(it) } ?: emptyList()

        val userDTO = UserDTO.fromEntity(authenticatedUser, dashboards, sales, products)
        return ResponseEntity.ok(userDTO)
    }

}