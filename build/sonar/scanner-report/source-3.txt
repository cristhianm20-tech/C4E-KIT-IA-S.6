package com.proyecto;

// ⚠️ Importaciones desorganizadas y mezcla de paquetes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class Application {
    
    // ⚠️ Variable global estática mutable
    public static final AtomicInteger GLOBAL_COUNTER = new AtomicInteger(0);
    
    // ⚠️ Método principal con configuración insegura
    public static void main(String[] args) {
        // ⚠️ Configuración de seguridad deshabilitada
        System.setProperty("spring.security.user.password", "admin123");
        SpringApplication.run(Application.class, args);
    }
    
    // ⚠️ Configuración CORS insegura
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
} 