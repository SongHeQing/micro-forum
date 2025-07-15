package com.songheqing.microforum.config;

import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer globalHeaderOpenApiCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
                .forEach(operation -> operation.addParametersItem(new Parameter()
                        .in("header")
                        .required(false)
                        .name("Authorization")
                        .description("JWT令牌，格式：直接填token或Bearer token")
                        .example(
                                "eyJhbGciOiJIUzM4NCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE3NTMxODYyMjJ9.6e5LVCQJsGwlBjK_mNoaSBXi3MbR3BKAXv3hKTL_iKReiLHvuKUhWFBmEgiws2rD"))));
    }
}