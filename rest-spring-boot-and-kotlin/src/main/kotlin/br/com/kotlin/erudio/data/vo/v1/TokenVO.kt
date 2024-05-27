package br.com.kotlin.erudio.data.vo.v1

import java.util.*

//token..
data class TokenVO(
    val username: String?=null,
    val authenticated: Boolean?=null,
    val created: Date?=null,
    val expired: Date?=null,
    val accessToken: String?=null,
    val refreshToken: String?=null,
)