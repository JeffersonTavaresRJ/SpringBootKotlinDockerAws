package br.com.kotlin.erudio.service

import br.com.kotlin.erudio.exception.model.RequiredObjectIsNullException
import br.com.kotlin.erudio.model.MockPerson
import br.com.kotlin.erudio.repository.PersonRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class PersonServiceTest {

    private lateinit var inputObject: MockPerson;

    @InjectMocks /*vai fazer o service verdadeiro*/
    private lateinit var service: PersonService;

    @Mock /*repository fake*/
    private lateinit var repository: PersonRepository;

    @BeforeEach
    fun setUp(){
        inputObject = MockPerson();
        MockitoAnnotations.openMocks(this);//abrindo os objetos mocados (ex.: repository)
    }

    @Test
    fun create() {
        val entity = inputObject.mockEntity(1);

        val persisted = entity.copy();
        persisted.id = 1;

        `when`(repository.save(entity)).thenReturn(persisted);

        val VO = inputObject.mockVO(1);
        service.create(VO);

        verify(repository).save(entity);/*verifica se o método "save()" foi executado dentro do service..*/


    }

    @Test
    fun createNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ){service.create(null)};

        val expectedMessage = "Não é permitido persistir um objeto nulo";
        val actualMessage = exception.message;

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    fun update() {

        val entity = inputObject.mockEntity(1);

        val persisted = entity.copy();
        persisted.id = 1;

        `when`(repository.save(entity)).thenReturn(persisted);

        val VO = inputObject.mockVO(1);
        service.update(VO);

        verify(repository).save(entity);/*verifica se o método "save()" foi executado dentro do service..*/

    }

    @Test
    fun updateNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ){service.update(null)};

        val expectedMessage = "Não é permitido persistir um objeto nulo";
        val actualMessage = exception.message;

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    fun delete() {


    }
/*
    @Test
    fun findAll() {
        val persons = inputObject.mockList()
        `when`(repository.findAll()).thenReturn(persons)

        val result = service.findAll();

        assertEquals(14, result.size);

        for(i in 0..13){
            val person = result[i];
            assertTrue(person.links.toString().contains("/person/%s".format(i)));
            assertEquals("first name Test%s".format(i), person.firstName);
            assertEquals("last name Test%s".format(i), person.lastName);
        }

    }
*/
    @Test
    fun findById() {

        val person = inputObject.mockEntity(1);
        person.id = 1L;
        `when`(repository.findById(1)).thenReturn(Optional.of(person));

        val result = service.findById(1);
        assertNotNull(result);
        assertNotNull(result.key);
        assertNotNull(result.lastName);
        assertTrue(result.links.toString().contains("/person/1"));

        assertEquals("first name Test1", result.firstName);
        assertEquals("last name Test1", result.lastName);
        assertEquals("feminino", result.gender);


    }
}