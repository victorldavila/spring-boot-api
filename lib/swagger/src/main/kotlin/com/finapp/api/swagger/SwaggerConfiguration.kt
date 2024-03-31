package com.finapp.api.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfiguration {

    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI()
            .addSecurityItem(
                SecurityRequirement()
                .addList(schemeName)
            ).components(
                Components()
                .addSecuritySchemes(
                    schemeName,
                    SecurityScheme()
                        .name(schemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .bearerFormat(bearerFormat)
                        .`in`(SecurityScheme.In.HEADER)
                        .scheme(scheme)
                )
            )
            .info(
                Info()
                    .title("Only Tap Api")
                    .version("1.0")
                    .description("Documentation APIs v1.0")
            )

    companion object {
        const val schemeName = "bearerAuth"
        const val bearerFormat = "JWT"
        const val scheme = "bearer"
    }
}