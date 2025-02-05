package com.ewersson.dashboard_bi_api.config

import jakarta.servlet.Filter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*

@Configuration
@EnableWebSecurity
class SecurityConfigurations {

    @Autowired
    lateinit var securityFilter: Filter

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors { }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/dashboards").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/sales/{productId}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/dashboards/user").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/products").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/products/all").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/products/{productID}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/sales/all").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/nlp/query").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/products/{productName}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "nlp/user").hasRole("USER")
                    .anyRequest().authenticated()
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://127.0.0.1:5500", "http://localhost:5500", "https://dashboard-bi-frontend.onrender.com", "https://bi-app-qvw1.onrender.com")
        configuration.allowedMethods = listOf("POST", "GET", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("Content-Type", "Authorization")
        val source: UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}