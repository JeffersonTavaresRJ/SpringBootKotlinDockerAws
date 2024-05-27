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
import br.com.kotlin.erudio.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserService(
    @field: Autowired var userRepository: UserRepository
) : UserDetailsService
{

    private val logger = Logger.getLogger(UserService::class.java.name)
    override fun loadUserByUsername(username: String?): UserDetails {
       logger.info("Buscando um usuário pelo nome $username...");

        val user = userRepository.findByUsername(username);
        return  user ?: throw UsernameNotFoundException("UserName $username não encontrado!");
    }
}