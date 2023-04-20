package com.turnoverdoc.turnover.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Origin",
                        "Accept",
                        "Content-Length",
                        "Accept-Encoding",
                        "X-CSRF-Token",
                        "Access-Control-Allow-Origin",
                        "Authorization",
                        "Content-Type",
                        "Access-Control-Expose-Headers",
                        "Access-Control-Allow-Headers",
                        "Set-Cookie")
                .allowCredentials(true);
    }
}
