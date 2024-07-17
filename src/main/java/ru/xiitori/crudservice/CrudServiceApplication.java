package ru.xiitori.crudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CrudServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudServiceApplication.class, args);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/auth/register").allowedOrigins("http://127.0.0.1:5500").allowCredentials(true);
                registry.addMapping("/auth/login").allowedOrigins("http://127.0.0.1:5500").allowCredentials(true);
                registry.addMapping("/profile/*").allowedOrigins("http://127.0.0.1:5500").allowCredentials(true);
            }
        };
    }
}
