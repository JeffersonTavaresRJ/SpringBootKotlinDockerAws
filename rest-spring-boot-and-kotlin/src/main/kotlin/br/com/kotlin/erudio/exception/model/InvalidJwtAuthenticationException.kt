package br.com.kotlin.erudio.exception.model

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import javax.security.sasl.AuthenticationException


//@ResponseStatus(HttpStatus.FORBIDDEN)
//class InvalidJwtAuthenticationException(exception:String): AuthenticationException(exception) {

//}

class InvalidJwtAuthenticationException(override val message: String, val internalCode: String): AuthenticationException(message) {

}