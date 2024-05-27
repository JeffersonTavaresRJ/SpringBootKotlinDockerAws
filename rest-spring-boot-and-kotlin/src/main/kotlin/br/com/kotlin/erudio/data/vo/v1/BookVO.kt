package br.com.kotlin.erudio.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel
import java.util.Date

data class BookVO(
    @Mapping("id")
    @field:JsonProperty("id")
    var key: Long=0,
    var author: String="",
    var launchDate: Date?=null,
    var price: Double=0.0,
    var title: String=""
): RepresentationModel<BookVO>()