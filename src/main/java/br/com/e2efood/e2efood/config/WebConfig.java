package br.com.e2efood.e2efood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/restaurantes/**") // mapeia o endpoint específico
                .allowedOrigins("http://127.0.0.1:8088") // permite a origem específica
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // métodos permitidos
    }
}