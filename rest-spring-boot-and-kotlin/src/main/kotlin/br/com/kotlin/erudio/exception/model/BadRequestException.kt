package br.com.kotlin.erudio.exception.model

class BadRequestException(override val message: String, val internalCode: String): Exception() {
}