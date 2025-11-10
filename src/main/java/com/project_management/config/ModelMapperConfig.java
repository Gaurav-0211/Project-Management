package com.project_management.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task, Project and Notification Management - API")
                        .version("1.0")
                        .description("API documentation for Task, Project and Notification System")
                        .contact(new Contact().email("gauravkr0211@gmail.com").name("Gaurav Kumar").url("abc@gmail.com")));
    }

}
