package com.ersubhadip.branchinternationalassignment.data.remote

import com.ersubhadip.branchinternationalassignment.data.pojos.ChatItem
import com.ersubhadip.branchinternationalassignment.data.pojos.LoginRequestBody
import com.ersubhadip.branchinternationalassignment.data.pojos.LoginResponse
import com.ersubhadip.branchinternationalassignment.data.pojos.SenderRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatAPI {
    @POST("api/login")
    suspend fun loginUser(@Body requestBody: LoginRequestBody): Response<LoginResponse>

    @GET("api/messages")
    suspend fun getChats(@Header("X-Branch-Auth-Token") auth_token: String): Response<List<ChatItem>>

    @POST("api/messages")
    suspend fun sendChat(@Body requestBody: SenderRequestBody): Response<List<ChatItem>>
}