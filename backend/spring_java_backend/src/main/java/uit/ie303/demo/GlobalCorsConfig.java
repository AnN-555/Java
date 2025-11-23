package uit.ie303.demo;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS policy to all endpoints
                .allowedOrigins("https://example.com", "null", "http://localhost:9090/api/", "http://localhost:3000") // Allowed origins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // HTTP methods
                .allowedHeaders("Content-Type"); // Allowed headers
                // .allowCredentials(true); // Allow credentials (cookies, Authorization headers, etc.)
    }
}
