package com.autoshopping.authenticate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // Permitir apenas o frontend React
        configuration.addAllowedMethod("*"); // Permitir todos os métodos (GET, POST, PUT, DELETE, etc.)
        configuration.addAllowedHeader("*"); // Permitir todos os cabeçalhos
        configuration.setAllowCredentials(true); // Permitir cookies e autenticação

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a configuração a todas as rotas
        return source;
    }
}
