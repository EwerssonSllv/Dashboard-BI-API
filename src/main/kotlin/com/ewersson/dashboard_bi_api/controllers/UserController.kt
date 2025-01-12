package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.config.TokenService
import com.ewersson.dashboard_bi_api.model.users.AuthenticationDTO
import com.ewersson.dashboard_bi_api.model.users.LoginResponseDTO
import com.ewersson.dashboard_bi_api.model.users.RegisterDTO
import com.ewersson.dashboard_bi_api.model.users.User
import com.ewersson.dashboard_bi_api.repositories.UserRepository
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.RequestBody


@RestController
@RequestMapping("auth")
class UserController
@Autowired
constructor(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val tokenService: TokenService,
    private val passwordEncoder: BCryptPasswordEncoder
){

    @PostMapping("/login")
    fun login(@RequestBody data: @Valid AuthenticationDTO,  response: HttpServletResponse): ResponseEntity<LoginResponseDTO> {
        val usernamePassword = UsernamePasswordAuthenticationToken(data.login, data.password)
        val auth = authenticationManager.authenticate(usernamePassword)

        val token = tokenService.generateToken(auth.principal as User)
        response.addHeader("Authorization", "Bearer $token")

        return ResponseEntity.ok(LoginResponseDTO(token))
    }

    @PostMapping("/register")
    fun register(@RequestBody data: @Valid RegisterDTO): ResponseEntity<Any> {
        if (userRepository.findByLogin(data.login) != null) return ResponseEntity.badRequest().build<Any>()

        val encryptedPassword = passwordEncoder.encode(data.password)
        val newUser = User(data.role, data.login, encryptedPassword, null)

        userRepository.save(newUser)

        return ResponseEntity.ok().build<Any>()
    }
}