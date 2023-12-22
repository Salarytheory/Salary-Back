package com.salary.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi authorizeApi() {
        return GroupedOpenApi.builder()
                .group("authorize")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(customGlobalOpenApiHeader())
                .pathsToExclude("/home/health", "/api/v1/member/login-state", "/api/v1/member/social-login", "/api/v1/jwt/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/home/health", "/api/v1/member/login-state", "/api/v1/member/social-login", "/api/v1/jwt/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("월급학개론 REST API Docs")
                        .description("월급학개론 REST API 명세서입니다.")
                        .version("0.1"));
    }

    @Bean
    public OpenApiCustomizer customGlobalOpenApiHeader() {
        Parameter userToken = new Parameter()
                .name("access-token")
                .in("header")
                .description("access-token 값")
                .required(true);

        return openApi -> openApi.getPaths().values().forEach(
                operation -> operation
                        .addParametersItem(userToken)
        );
    }
}
