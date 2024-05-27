package br.com.kotlin.erudio.exception.model

data class ErrorResponse (
    var httpCode: Int,
    var message: String?,
    var internalCode: String?,
    var errors : List<FieldErrorResponse>?
)