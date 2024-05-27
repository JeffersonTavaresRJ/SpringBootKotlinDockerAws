package br.com.kotlin.erudio.integrations.controller.cors.withxml

import br.com.kotlin.erudio.integrations.data.vo.v1.*
import br.com.kotlin.erudio.integrations.TestConfigs
import br.com.kotlin.erudio.integrations.testcontainers.AbstractIntegration
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerCorsWithXML() : AbstractIntegration() {

    private lateinit var specification: RequestSpecification
    private lateinit var objectMapper: ObjectMapper
    private lateinit var person: PersonVO

    private lateinit var token: String

    @BeforeAll
    fun setupTests(){
        objectMapper = ObjectMapper()
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        person = PersonVO()
        token = ""
    }

    @Test
    @Order(0)
    fun authorization() {
        val user = AccountCredentialsVO(
            username = "Jefferson",
            password = "adminadmin"
        )

        token = given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_XML)
            .body(user)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()//extrair o body da requisição e retorna como TokenVO..
            .body()
            .`as`(TokenVO::class.java)
            .accessToken!!
    }

    @Test
    @Order(1)
    fun testCreate() {
        mockPerson()

        specification = RequestSpecBuilder()
            .addHeader(
                TestConfigs.HEADER_PARAM_ORIGIN,
                TestConfigs.ORIGIN_ERUDIO
            )
            .addHeader(
                TestConfigs.HEADER_PARAM_AUTHORIZATION,
                "Bearer $token"
            )
            .setBasePath("/api/person")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_XML)
            .body(person)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val createdPerson = objectMapper.readValue(
            content,
            PersonVO::class.java
        )
        person = createdPerson

        assertNotNull(createdPerson.key)
        assertNotNull(createdPerson.firstName)
        assertNotNull(createdPerson.lastName)
        assertNotNull(createdPerson.address)
        assertNotNull(createdPerson.gender)

        assertTrue(createdPerson.key > 0)

        assertEquals("Nelson", createdPerson.firstName)
        assertEquals("Piquet", createdPerson.lastName)
        assertEquals("Brasília, DF, Brasil", createdPerson.address)
        assertEquals("Male", createdPerson.gender)
    }

    @Test
    @Order(2)
    fun testCreateWithWrongOrigin() {
        mockPerson()

        specification = RequestSpecBuilder()
            .addHeader(
                TestConfigs.HEADER_PARAM_ORIGIN,
                TestConfigs.ORIGIN_SEMERU
            )
            .addHeader(
                TestConfigs.HEADER_PARAM_AUTHORIZATION,
                "Bearer $token"
            )
            .setBasePath("/api/person")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_XML)
            .body(person)
            .`when`()
            .post()
            .then()
            .statusCode(403)
            .extract()
            .body()
            .asString()

        assertEquals("Invalid CORS request", content)
    }

    @Test
    @Order(3)
    fun testFindById() {


        specification = RequestSpecBuilder()
            .addHeader(
                TestConfigs.HEADER_PARAM_ORIGIN,
                TestConfigs.ORIGIN_ERUDIO
            )
            .addHeader(
                TestConfigs.HEADER_PARAM_AUTHORIZATION,
                "Bearer $token"
            )
            .setBasePath("/api/person")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_XML)
            .pathParam("id", 2)
            .`when`()["{id}"]
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val createdPerson = objectMapper.readValue(
            content,
            PersonVO::class.java
        )
        assertNotNull(createdPerson.key)
        assertNotNull(createdPerson.firstName)
        assertNotNull(createdPerson.lastName)
        assertNotNull(createdPerson.address)
        assertNotNull(createdPerson.gender)

        assertTrue(createdPerson.key > 0)

        assertEquals("Jefferson", createdPerson.firstName)
        assertEquals("Silva", createdPerson.lastName)
        assertEquals("Endereço Natal-rn", createdPerson.address)
        assertEquals("Male", createdPerson.gender)
    }

    @Test
    @Order(4)
    fun testFindByIdWithWrongOrigin() {
        mockPerson()

        specification = RequestSpecBuilder()
            .addHeader(
                TestConfigs.HEADER_PARAM_ORIGIN,
                TestConfigs.ORIGIN_SEMERU
            )
            .addHeader(
                TestConfigs.HEADER_PARAM_AUTHORIZATION,
                "Bearer $token"
            )
            .setBasePath("/api/person")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_XML)
            .pathParam("id", person.key)
            .`when`()["{id}"]
            .then()
            .statusCode(403)
            .extract()
            .body()
            .asString()

        assertEquals("Invalid CORS request", content)
    }

    private fun mockPerson() {
        person.firstName = "Nelson"
        person.lastName = "Piquet"
        person.address = "Brasília, DF, Brasil"
        person.gender = "Male"
    }
}