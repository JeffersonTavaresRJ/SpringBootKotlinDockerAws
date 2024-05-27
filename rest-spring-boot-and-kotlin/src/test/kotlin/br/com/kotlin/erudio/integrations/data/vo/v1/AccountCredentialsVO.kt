package br.com.kotlin.erudio.integrations.data.vo.v1

import jakarta.xml.bind.annotation.XmlRootElement

//login...
@XmlRootElement
data class AccountCredentialsVO(
    var username: String?=null,
    var password: String?=null,
)