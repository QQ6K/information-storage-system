package ru.task.iss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.task.iss.security.service.AuthService;

@SpringBootApplication
public class InformationStorageSystemApplication extends SpringBootServletInitializer {



	/*@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// Don't do this in production, use a proper list  of allowed origins
		//config.setAllowedOrigins(Collections.singletonList("*"));
		config.setAllowedOrigins(List.of("http://localhost:3000"));
		//config.addAllowedOriginPattern("*");
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept","Access-Control-Allow-Headers"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}*/

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowCredentials(true)
						.allowedOrigins("http://localhost:3000");
			}
		};
	}

	public static void main(String[] args) {



		SpringApplication.run(InformationStorageSystemApplication.class, args);

	}

}
