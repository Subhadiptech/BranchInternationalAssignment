package com.ersubhadip.branchinternationalassignment.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ersubhadip.branchinternationalassignment.data.pojos.LoginResponse
import com.ersubhadip.branchinternationalassignment.repository.ChatRepository
import com.ersubhadip.branchinternationalassignment.repository.StandardResponse
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: ChatRepository) : ViewModel() {

    val loginResponse: StateFlow<StandardResponse<LoginResponse>>
        get() = repository.loginResponse


    fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            repository.userLogin(username, password)
        }
    }
}