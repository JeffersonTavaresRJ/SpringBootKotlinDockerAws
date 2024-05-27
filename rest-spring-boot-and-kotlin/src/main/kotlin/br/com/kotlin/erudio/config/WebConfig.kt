package br.com.kotlin.erudio.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer{

    @Value("\${cors.originPatterns:default}")
    private val corsOriginPatterns: String="";

    /*configuração para tratar o formato do response das consultas (json, xml yml, etc..)*/
    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {

        /*passando como parâmetro do endpoint da api: mediaType..*/
        /*
        configurer.favorParameter(true)
            .parameterName("mediaType")
            .ignoreAcceptHeader(true)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML);

         */

        /*passando no header..*/
        configurer.favorParameter(false)
            .ignoreAcceptHeader(false)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML);

    }

    @Bean
    fun addCorsConfig(): WebMvcConfigurer{
        return object : WebMvcConfigurer{
            override fun addCorsMappings(registry: CorsRegistry) {
                val allowedOrigins = corsOriginPatterns.split(",").toTypedArray();
                registry.addMapping("/**")
                    .allowedMethods("*")//POST, GET, PUT e DELETE...
                    .allowedOrigins(*allowedOrigins)
                    .allowCredentials(true);
             }
        }
    }
}