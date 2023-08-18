package com.ersubhadip.branchinternationalassignment.data.pojos

data class LoginResponse(
    val auth_token: String?
)

data class ChatItem(
    val id: Int?,
    val thread_id: Int?,
    val user_id: String?,
    val body: String?,
    val timestamp: String?,
    val agent_id: String?
)