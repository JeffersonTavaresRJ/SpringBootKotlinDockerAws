package br.com.kotlin.erudio.repository

import br.com.kotlin.erudio.integrations.testcontainers.AbstractIntegration
import br.com.kotlin.erudio.model.Person
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonRepositoryTest : AbstractIntegration() {

    @Autowired
    private lateinit var repository: PersonRepository

    private lateinit var person: Person

    //@BeforeAll
    //fun setup(){
    //    person = Person()
    //}

    @Test
    @Order(0)
    fun testFindByName() {
        val pageable: Pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "firstName"))

        person = repository.findPersonByName("je", pageable).content[0]

        assertNotNull(person)
        assertNotNull(person.id)
        assertNotNull(person.firstName)
        assertNotNull(person.lastName)
        assertNotNull(person.address)
        assertNotNull(person.gender)
        assertEquals("Marje", person.firstName)
        assertEquals("Leynton", person.lastName)
        assertEquals("7 Corry Alley", person.address)
        assertEquals("Female", person.gender)
        assertEquals(true, person.enabled)
    }

    @Test
    @Order(1)
    fun testDisablePerson() {
        repository.disablePerson(person.id)
        person = repository.findById(person.id).get()

        assertNotNull(person)
        assertNotNull(person.id)
        assertNotNull(person.firstName)
        assertNotNull(person.lastName)
        assertNotNull(person.address)
        assertNotNull(person.gender)
        assertEquals("Marje", person.firstName)
        assertEquals("Leynton", person.lastName)
        assertEquals("7 Corry Alley", person.address)
        assertEquals("Female", person.gender)
        assertEquals(false, person.enabled)
    }
}