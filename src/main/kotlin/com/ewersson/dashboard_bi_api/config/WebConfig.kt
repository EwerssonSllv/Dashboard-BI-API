package com.ewersson.dashboard_bi_api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://127.0.0.1:5500", "https://bi-app-qvw1.onrender.com")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .exposedHeaders("Authorization")
    }
}
