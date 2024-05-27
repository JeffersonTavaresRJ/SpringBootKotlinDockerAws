package br.com.kotlin.erudio.exception.model

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectIsNullException: RuntimeException {
    constructor():super("Não é permitido persistir um objeto nulo")
    constructor(exception: String?):super(exception)
}