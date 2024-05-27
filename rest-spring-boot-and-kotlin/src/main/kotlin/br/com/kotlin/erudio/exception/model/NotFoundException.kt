package br.com.kotlin.erudio.exception.model

class NotFoundException(override val message: String, val internalCode: String): Exception(){}