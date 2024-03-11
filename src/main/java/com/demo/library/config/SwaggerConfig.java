package com.demo.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI theOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Library")
                .description("A library system demo.")
        );
    }
}
