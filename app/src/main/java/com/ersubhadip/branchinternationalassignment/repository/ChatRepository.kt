package com.ersubhadip.branchinternationalassignment.repository

import com.ersubhadip.branchinternationalassignment.data.pojos.ChatItem
import com.ersubhadip.branchinternationalassignment.data.pojos.LoginRequestBody
import com.ersubhadip.branchinternationalassignment.data.pojos.LoginResponse
import com.ersubhadip.branchinternationalassignment.data.pojos.SenderRequestBody
import com.ersubhadip.branchinternationalassignment.data.remote.ChatAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatRepository(private val apis: ChatAPI) {
    private var _loginResponse =
        MutableStateFlow<StandardResponse<LoginResponse>>(StandardResponse(false, null))
    val loginResponse: StateFlow<StandardResponse<LoginResponse>> get() = _loginResponse

    private var _chatList =
        MutableStateFlow<StandardResponse<List<ChatItem>>>(StandardResponse(false, null))
    val chatList: StateFlow<StandardResponse<List<ChatItem>>> get() = _chatList

    private var _sendersChatList =
        MutableStateFlow<StandardResponse<List<ChatItem>>>(StandardResponse(false, null))
    val sendersChatList: StateFlow<StandardResponse<List<ChatItem>>> get() = _chatList


    suspend fun userLogin(username: String, password: String) {
        val resp = apis.loginUser(LoginRequestBody(username = username, password = password))
        if (resp.isSuccessful && resp.body() != null) {
            _loginResponse.emit(
                StandardResponse(
                    success = true,
                    payload = resp.body()
                )
            )
        } else {
            _loginResponse.emit(
                StandardResponse(
                    success = false,
                    payload = null
                )
            )
        }
    }

    suspend fun getChats(auth: String) {
        val resp = apis.getChats(auth)
        if (resp.isSuccessful && resp.body() != null) {
            _chatList.emit(
                StandardResponse(
                    success = true,
                    payload = resp.body()
                )
            )
        } else {
            _chatList.emit(
                StandardResponse(
                    success = false,
                    payload = null
                )
            )
        }
    }


    suspend fun sendChat(id: Int, message: String) {
        val resp = apis.sendChat(SenderRequestBody(id = id, body = message))
        if (resp.isSuccessful && resp.body() != null) {
            _sendersChatList.emit(
                StandardResponse(
                    success = true,
                    payload = resp.body()
                )
            )
        } else {
            _sendersChatList.emit(
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