package br.com.kotlin.erudio.exception.enun

enum class Errors(val cod: String, val message: String) {
    ER000("ER000", "O Id da pessoa [%s] não existe"),
    ER001("ER001", "Dados não encontrados")
}