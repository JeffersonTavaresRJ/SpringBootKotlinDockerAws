package br.com.kotlin.erudio.controller

import br.com.kotlin.erudio.data.vo.v1.PersonVO
import br.com.kotlin.erudio.service.PersonService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/person")
//configuração do swagger para o spring-doc..
@Tag(name="Pessoas", description = "Endpoints de pessoas")
class PersonController {
    @Autowired
    private lateinit var personService: PersonService

    @PostMapping
    fun create(@RequestBody p: PersonVO):PersonVO{
        return personService.create(p);
    }

    @PutMapping
    fun update(@RequestBody p: PersonVO):PersonVO{
        return personService.update(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable(name = "id") id: Long){
        personService.delete(id);
    }

    @GetMapping(produces=[MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    //configurações do swagger para o spring-doc..
    @Operation(summary = "Busca pessoas",
               description = "Busca todas as pessoas",
               tags = ["Pessoas"],
               responses = [ApiResponse(
                   description = "Success",
                   responseCode = "200",
                   content = [Content(array = ArraySchema(schema = Schema(implementation = PersonVO::class)))]
               ),
               ApiResponse(description = "Dados não encontrados",
                           responseCode = "204",
                           content = [Content(schema = Schema(implementation = Unit::class))])])
    fun getAll(@RequestParam(value = "page", defaultValue = "0") page: Int,
               @RequestParam(value = "size", defaultValue = "12") size: Int,
               @RequestParam(value = "direction", defaultValue = "asc") direction: String): PagedModel<EntityModel<PersonVO>> {

        val sortDirection = if("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC;
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return personService.findAll(pageable);

    }

    @GetMapping(value=["/findByName/{firstName}"], produces=[MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getByFirstName(@PathVariable(value = "firstName") firstName: String,
                       @RequestParam(value = "page", defaultValue = "0") page: Int,
                       @RequestParam(value = "size", defaultValue = "12") size: Int,
                       @RequestParam(value = "direction", defaultValue = "asc") direction: String): PagedModel<EntityModel<PersonVO>> {

        val sortDirection = if("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC;
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return personService.findPersonByName(firstName, pageable);

    }

    @GetMapping(value=["/{id}"],produces=[MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getById(@PathVariable(name = "id") id: Long): PersonVO{
        return personService.findById(id);
    }

    @PatchMapping(value=["/{id}"],produces=[MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun disablePerson(@PathVariable(name = "id") id: Long): PersonVO{
        return personService.disablePerson(id);
    }
}