package br.com.kotlin.erudio.service

import br.com.kotlin.erudio.controller.BookController
import br.com.kotlin.erudio.controller.PersonController
import br.com.kotlin.erudio.data.vo.v1.BookVO
import br.com.kotlin.erudio.exception.enun.Errors
import br.com.kotlin.erudio.exception.model.NotFoundException
import br.com.kotlin.erudio.exception.model.RequiredObjectIsNullException
import br.com.kotlin.erudio.mapper.DozerMapper
import br.com.kotlin.erudio.model.Book
import br.com.kotlin.erudio.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service

@Service
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository;

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<BookVO> //para converter os VOs do findAll

    fun create(b: BookVO?){
        if (b==null)throw RequiredObjectIsNullException();
        bookRepository.save(DozerMapper.parseObject(b, Book:: class.java));
    }

    fun update(b: BookVO?){
        if (b==null)throw RequiredObjectIsNullException();
        bookRepository.save(DozerMapper.parseObject(b, Book:: class.java));
    }

    fun delete(id: Long){
        bookRepository.deleteById(id);
    }

    fun findAll(pageable: Pageable): PagedModel<EntityModel<BookVO>> {
        val result = bookRepository.findAll(pageable);
        val bookVOs = result.map { b-> DozerMapper.parseObject(b, BookVO:: class.java) }
        bookVOs.map { b-> b.add(linkTo(BookController::class.java).slash(b.key).withSelfRel()) }
        return assembler.toModel(bookVOs);
    }

    fun findById(id: Long): BookVO{
        val b = bookRepository.findById(id).orElseThrow{NotFoundException(Errors.ER000.cod, Errors.ER000.message.format(id.toString())) };
        val bookVO: BookVO =  DozerMapper.parseObject(b, BookVO:: class.java );
        val whihtSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel();
        bookVO.add(whihtSelfRel);
        return bookVO;
    }
}