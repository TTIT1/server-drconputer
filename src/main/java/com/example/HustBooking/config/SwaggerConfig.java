package com.example.HustBooking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setName("HustBooking API");
        contact.setEmail("your.email@example.com");
        contact.setUrl("https://www.yourwebsite.com");

        Info info = new Info()
                .title("HustBooking User Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints for managing users.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
} /*
Truy cập Swagger UI tại các URL sau:
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs (để xem OpenAPI specification) */