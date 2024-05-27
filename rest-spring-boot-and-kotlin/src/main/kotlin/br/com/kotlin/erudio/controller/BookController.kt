package br.com.kotlin.erudio.controller

import br.com.kotlin.erudio.data.vo.v1.BookVO
import br.com.kotlin.erudio.data.vo.v1.PersonVO
import br.com.kotlin.erudio.service.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book")
//configuração do swagger para o spring-doc..
@Tag(name="Livros", description = "Endpoints de livros")
class BookController {
    @Autowired
    private lateinit var bookService: BookService

    @PostMapping
    fun create(@RequestBody b: BookVO){
        bookService.create(b);
    }

    @PutMapping
    fun update(@RequestBody b: BookVO){
        bookService.update(b);
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(name = "id") id: Long){
        bookService.delete(id);
    }

    @GetMapping(produces=[MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])

    //configurações do swagger para o spring-doc..
    @Operation(summary = "Busca livros",
               description = "Busca todos os livros",
               tags = ["Livros"],
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
               @RequestParam(value = "direction", defaultValue = "asc") direction: String): PagedModel<EntityModel<BookVO>> {

        val sortDirection = if("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC;
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));

        return bookService.findAll(pageable);
    }

    @GetMapping(value=["/{id}"],produces=[MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getById(@PathVariable(name = "id") id: Long): BookVO{
        return bookService.findById(id);
    }
}