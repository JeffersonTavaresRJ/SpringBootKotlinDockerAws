package br.com.kotlin.erudio.service

import br.com.kotlin.erudio.controller.PersonController
import br.com.kotlin.erudio.data.vo.v1.PersonVO
import br.com.kotlin.erudio.exception.enun.Errors
import br.com.kotlin.erudio.exception.model.NotFoundException
import br.com.kotlin.erudio.exception.model.RequiredObjectIsNullException
import br.com.kotlin.erudio.mapper.DozerMapper
import br.com.kotlin.erudio.model.Person
import br.com.kotlin.erudio.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonService {
    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<PersonVO>;//para converter os VOs do findAll

    fun create(p: PersonVO?): PersonVO{
        if (p==null)throw RequiredObjectIsNullException();
        val person = personRepository.save(DozerMapper.parseObject(p, Person:: class.java));
        return DozerMapper.parseObject(person, PersonVO:: class.java);
    }

    fun update(p: PersonVO?):PersonVO{
        if (p==null)throw RequiredObjectIsNullException();
        val person = personRepository.save(DozerMapper.parseObject(p, Person:: class.java));
        return DozerMapper.parseObject(person, PersonVO:: class.java);
    }

    @Transactional
    fun disablePerson(id: Long): PersonVO{
        personRepository.disablePerson(id);
        return findById(id);
    }

    fun delete(id: Long){
        personRepository.deleteById(id);
    }

    fun findAll(pageable: Pageable): PagedModel<EntityModel<PersonVO>> {
        val result = personRepository.findAll(pageable)// pageable é default do repository..
        return entityModels(result)
    }

    fun findPersonByName(firstName :String, pageable: Pageable): PagedModel<EntityModel<PersonVO>> {
        val result = personRepository.findPersonByName(firstName, pageable)// pageable é default do repository..
        return entityModels(result)
    }

    fun findById(id: Long): PersonVO{
        val p = personRepository.findById(id).orElseThrow{NotFoundException(Errors.ER000.cod, Errors.ER000.message.format(id.toString())) };
        val personVO: PersonVO =  DozerMapper.parseObject(p, PersonVO:: class.java );
        val whihtSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel();
        personVO.add(whihtSelfRel);
        return personVO;
    }

    private fun entityModels(result: Page<Person>): PagedModel<EntityModel<PersonVO>> {
        val personVOs = result.map {p -> DozerMapper.parseObject(p, PersonVO::class.java) }//iterando dentro do array, convertendo para PersonVO..
        personVOs.map {p ->  p.add(linkTo(PersonController::class.java).slash(p.key).withSelfRel()) } //iterando dentro do array, adicionando link..
        return assembler.toModel(personVOs);
    }
}