package br.com.kotlin.erudio.integrations.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dozermapper.core.Mapping
import jakarta.xml.bind.annotation.XmlRootElement
import org.springframework.hateoas.RepresentationModel
import java.util.Date

@XmlRootElement
data class BookVO(
    @Mapping("id")
    @field:JsonProperty("id")
    var key: Long=0,
    var author: String="",
    var launchDate: Date?=null,
    var price: Double=0.0,
    var title: String=""
): RepresentationModel<BookVO>()