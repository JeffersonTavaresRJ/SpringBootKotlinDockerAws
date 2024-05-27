package br.com.kotlin.erudio.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.annotations.OpenAPI31
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiSwaggerConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("RestFull API Swagger Curso Kotlin")
                    .version("v1")
                    .description("RestFull API do curso Kotlin com swagger")
                    .termsOfService("https://pub.erudio.com.br/meus-cursos")
                    .license(
                        License().name("Apache 2.0").url("https://pub.erudio.com.br/meus-cursos")
                    )
            )
    }
}