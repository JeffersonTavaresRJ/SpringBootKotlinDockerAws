package br.com.kotlin.erudio.repository

import br.com.kotlin.erudio.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<User?, Long?> {

    //query do java JPA
    //a função não precisa de corpo, pq ela vai pegar o parâmetro, executar aquery do jpa e
    //vai retornar o objeto User na function..
    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    fun findByUsername(@Param("userName") userName:String?):User?
}