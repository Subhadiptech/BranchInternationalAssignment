package com.ersubhadip.branchinternationalassignment.data.pojos

data class LoginRequestBody(
    val username: String,
    val password: String
)

data class SenderRequestBody(
    val thread_id: Int,
    val body: String
)