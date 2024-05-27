package br.com.kotlin.erudio.repository

import br.com.kotlin.erudio.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<Book, Long> {
}