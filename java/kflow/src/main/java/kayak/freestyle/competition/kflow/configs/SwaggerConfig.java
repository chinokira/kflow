package kayak.freestyle.competition.kflow.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kflow API Documentation")
                        .version("1.0")
                        .description("Documentation pour les endpoints de l'application Kflow."));
    }
}
