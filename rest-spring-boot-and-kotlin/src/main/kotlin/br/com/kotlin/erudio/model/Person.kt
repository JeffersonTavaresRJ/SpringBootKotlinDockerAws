package br.com.kotlin.erudio.model

import jakarta.persistence.*

@Entity
@Table(name = "persons")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long=0,
    @Column(nullable = false, length = 20)
    var firstName: String,
    @Column(nullable = false, length = 100)
    var lastName: String,
    @Column(nullable = false, length = 100)
    var address: String,
    @Column(nullable = false, length = 1)
    var gender: String,
    @Column(nullable = false)
    var enabled: Boolean=true
)