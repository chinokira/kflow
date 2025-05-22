package kayak.freestyle.competition.kflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main application class for K-FLOW (Kayak Freestyle Leaderboard Organizer & Webmanager).
 * This application manages kayak freestyle competitions, including athlete registration,
 * competition organization, and leaderboard management.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@SpringBootApplication
public class KflowApplication {

    /**
     * Main entry point of the K-FLOW application.
     * Initializes and runs the Spring Boot application.
     *
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(KflowApplication.class, args);
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
     * Allows requests from the frontend application running on localhost:4200.
     *
     * @return WebMvcConfigurer bean with CORS configuration
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "UPDATE", "DELETE", "OPTIONS")
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }

}
