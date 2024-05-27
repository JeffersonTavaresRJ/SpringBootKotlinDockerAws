package br.com.kotlin.erudio.integrations.data.vo.v1

import jakarta.xml.bind.annotation.XmlRootElement
import java.util.*

//token..
@XmlRootElement
data class TokenVO(
    var username: String?=null,
    var authenticated: Boolean?=null,
    var created: Date?=null,
    var expired: Date?=null,
    var accessToken: String?=null,
    var refreshToken: String?=null,
)