package br.com.kotlin.erudio.integrations.swagger

import br.com.kotlin.erudio.integrations.ConfigTests
import br.com.kotlin.erudio.integrations.testcontainers.AbstractIntegration
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest():AbstractIntegration() {
    @Test
    fun shoulDisplaySwaggerUiPage(){
        val content = given()
            .basePath("/swagger-ui/index.html")
            .port(ConfigTests.SERVER_PORT)
            .`when`()
            .get()
            .then()
                 .statusCode(200)
            .extract()
            .body()
                 .asString();

        assertTrue(content.contains("Swagger UI"));



    }
}