package com.th3hero.projectmanagerserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
public class CorsConfiguration implements WebFluxConfigurer {

    @Value("app.url:*")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
          .allowedOrigins(allowedOrigins)
          .allowedMethods("GET", "POST", "DELETE")
          .allowedHeaders("*")
          .maxAge(3600);
    }
}