package com.ewersson.dashboard_bi_api.controllers

import com.ewersson.dashboard_bi_api.config.TokenService
import com.ewersson.dashboard_bi_api.model.users.*
import com.ewersson.dashboard_bi_api.repositories.UserRepository
import com.ewersson.dashboard_bi_api.service.exception.ErrorResponseDTO
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class UserController
@Autowired
constructor(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val tokenService: TokenService,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid data: AuthenticationDTO): ResponseEntity<Any> {
        return try {
            val usernamePassword = UsernamePasswordAuthenticationToken(data.login, data.password)
            val auth = authenticationManager.authenticate(usernamePassword)

            val token = tokenService.generateToken(auth.principal as User)
            ResponseEntity.ok(LoginResponseDTO(token))
        } catch (e: BadCredentialsException) {
            ResponseEntity.badRequest().body(ErrorResponseDTO("Invalid credentials!"))
        } catch (e: UsernameNotFoundException) {
            ResponseEntity.badRequest().body(ErrorResponseDTO("User not found!"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponseDTO("Internal server error!"))
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody @Valid data: RegisterDTO): ResponseEntity<Any> {
        if (userRepository.findByLogin(data.login) != null) {
            return ResponseEntity.badRequest().body(ErrorResponseDTO("User already exists!"))
        }
        val encryptedPassword = passwordEncoder.encode(data.password)

        val newUser = User(
            state = data.state,
            role = UserRole.USER,
            login = data.login,
            password = encryptedPassword,
            dashboards = null
        )

        userRepository.save(newUser)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

}
