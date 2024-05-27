package br.com.kotlin.erudio.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dozermapper.core.Mapping
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.metamodel.RepresentationMode
import org.springframework.hateoas.RepresentationModel

data class PersonVO(
    @Mapping("id")
    @field:JsonProperty("id")
    var key: Long=0,
    var firstName: String="",
    var lastName: String="",
    var address: String="",
    var gender: String="",
    var enabled: Boolean=true
): RepresentationModel<PersonVO>()