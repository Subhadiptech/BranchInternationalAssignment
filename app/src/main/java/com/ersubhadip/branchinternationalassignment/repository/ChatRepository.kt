package com.ersubhadip.branchinternationalassignment.repository

import com.ersubhadip.branchinternationalassignment.data.pojos.ChatItem
import com.ersubhadip.branchinternationalassignment.data.pojos.LoginRequestBody
import com.ersubhadip.branchinternationalassignment.data.pojos.LoginResponse
import com.ersubhadip.branchinternationalassignment.data.pojos.SenderRequestBody
import com.ersubhadip.branchinternationalassignment.data.remote.ChatAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ChatRepository @Inject constructor(private val apis: ChatAPI) {

    suspend fun loginUser(
        username: String,
        password: String
    ): Flow<StandardResponse<LoginResponse>> = flow {
        val loginUserResponse =
            apis.loginUser(LoginRequestBody(username = username, password = password))

        if (loginUserResponse.isSuccessful && loginUserResponse.body() != null) {
            emit(
                StandardResponse(
                    success = true,
                    payload = loginUserResponse.body()
                )
            )
        } else {
            emit(
                StandardResponse(
                    success = false,
                    payload = null
                )
            )
        }

    }

    suspend fun getChats(auth: String): Flow<StandardResponse<List<ChatItem>>> = flow {
        val resp = apis.getChats(auth)
        if (resp.isSuccessful && resp.body() != null) {
            emit(
                StandardResponse(
                    success = true,
                    payload = resp.body()
                )
            )
        } else {
            emit(
                StandardResponse(
                    success = false,
                    payload = null
                )
            )
        }
    }


    suspend fun sendChat(
        auth: String,
        id: Int,
        message: String
    ): Flow<StandardResponse<List<ChatItem>>> = flow {
        val resp = apis.sendChat(auth, SenderRequestBody(id = id, body = message))
        if (resp.isSuccessful && resp.body() != null) {
            emit(
                StandardResponse(
                    success = true,
                    payload = resp.body()
                )
            )
        } else {
            emit(
                StandardResponse(
                    success = false,
                    payload = null
                )
            )
        }
    }
}

data class StandardResponse<T>(
    val success: Boolean,
    val payload: T?
)