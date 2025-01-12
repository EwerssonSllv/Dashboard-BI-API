package com.ewersson.dashboard_bi_api.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.ewersson.dashboard_bi_api.model.users.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {
    @Value("\${api.security.token.secret}")
    private val secret: String? = null

    fun generateToken(user: User): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            val token = JWT.create()
                .withIssuer("dashboard-bi-api")
                .withSubject(user.getUserLogin())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm)
            return token
        } catch (exception: JWTCreationException) {
            throw RuntimeException("Error while generating token", exception)
        }
    }

    fun validateToken(token: String?): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            return JWT.require(algorithm)
                .withIssuer("dashboard-bi-api")
                .build()
                .verify(token)
                .subject
        } catch (exception: JWTVerificationException) {
            return ""
        }
    }

    private fun genExpirationDate(): Instant {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"))
    }
}