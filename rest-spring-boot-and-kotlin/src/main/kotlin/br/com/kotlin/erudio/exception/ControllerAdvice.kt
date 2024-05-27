package br.com.kotlin.erudio.exception

import br.com.kotlin.erudio.exception.model.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.lang.*

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
            httpCode = ex.hashCode(),
            message = ex.message,
            internalCode = null,
            errors = null
        )

        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
            httpCode = HttpStatus.NOT_FOUND.value(),
            message = ex.message,
            internalCode = ex.internalCode,
            errors = null
        )

        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleNotFoundException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
            httpCode = HttpStatus.BAD_REQUEST.value(),
            message = ex.message,
            internalCode = ex.internalCode,
            errors = null
        )

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RequiredObjectIsNullException::class)
    fun handleRequiredObjectIsNullException(ex: RequiredObjectIsNullException, request: WebRequest): ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
            httpCode = HttpStatus.NOT_FOUND.value(),
            message = ex.message,
            internalCode = null,
            errors = null
        )

        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidJwtAuthenticationException::class)
    fun handleInvalidJwtAuthenticationException(ex: InvalidJwtAuthenticationException, request: WebRequest): ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
            httpCode = HttpStatus.FORBIDDEN.value(),
            message = ex.message,
            internalCode = ex.internalCode,
            errors = null
        )

        return ResponseEntity(error, HttpStatus.FORBIDDEN)
    }
}