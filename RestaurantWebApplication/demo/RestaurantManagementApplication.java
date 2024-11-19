package com.RestaurantWebApplication.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RestaurantManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagementApplication.class, args);
	}

	@Configuration
	public class WebConfig implements WebMvcConfigurer {
		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedOrigins("http://localhost:63342")
							.allowedMethods("GET", "POST", "PUT", "DELETE")
							.allowedHeaders("*")
							.allowCredentials(true);
				}
			};
		}
	}
}



