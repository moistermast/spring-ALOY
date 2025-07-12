package com.sda.java3.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logEnvironmentVariables() {
		System.out.println("=== Environment Variables Check ===");
		System.out.println("DATABASE_URL: " + System.getenv("DATABASE_URL"));
		System.out.println("DB_USERNAME: " + System.getenv("DB_USERNAME"));
		System.out.println("DB_PASSWORD: " + (System.getenv("DB_PASSWORD") != null ? "***SET***" : "NULL"));
		System.out.println("SPRING_PROFILES_ACTIVE: " + System.getenv("SPRING_PROFILES_ACTIVE"));
		System.out.println("==================================");
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("GET", "PUT", "DELETE", "POST")
					.allowedHeaders("*");
			}
		};
	}
}
