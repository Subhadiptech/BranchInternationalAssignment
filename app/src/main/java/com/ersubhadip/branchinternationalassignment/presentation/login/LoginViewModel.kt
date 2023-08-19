package com.ersubhadip.branchinternationalassignment.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ersubhadip.branchinternationalassignment.data.local.Session
import com.ersubhadip.branchinternationalassignment.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val localData: Session
) : ViewModel() {

    private val _usernameState = mutableStateOf("")
    private val _passwordState = mutableStateOf("")

    val usernameState: State<String> = _usernameState
    val passwordState: State<String> = _passwordState

    val loginViewModelToLoginScreenEvents = Channel<ViewModelToLoginScreenEvents>()


    fun userLogin() {
        viewModelScope.launch {
            if (_usernameState.value.isNotEmpty() && _passwordState.value.isNotEmpty()) {
                repository.loginUser(
                    username = _usernameState.value,
                    password = _passwordState.value
                ).collectLatest { loginResponse ->
                    if (loginResponse.success) {
                        setUserAuth(loginResponse.payload?.auth_token.toString())
                        emitLoginScreenEvents(ViewModelToLoginScreenEvents.NavigateToHomeScreen)
                    } else {
                        emitLoginScreenEvents(
                            ViewModelToLoginScreenEvents.InvalidResponseError(
                                "Something went wrong"
                            )
                        )
                    }
                }
            } else {
                emitLoginScreenEvents(
                    ViewModelToLoginScreenEvents.InvalidResponseError(
                        "Enter all the details"
                    )
                )
            }
        }
    }

    fun onUsernameChange(newUsername: String) {
        _usernameState.value = newUsername.trim()
    }

    fun onPasswordChange(newPassword: String) {
        _passwordState.value = newPassword.trim()
    }

    private fun emitLoginScreenEvents(event: ViewModelToLoginScreenEvents) {
        viewModelScope.launch {
            when (event) {
                ViewModelToLoginScreenEvents.NavigateToHomeScreen -> {
                    loginViewModelToLoginScreenEvents.send(ViewModelToLoginScreenEvents.NavigateToHomeScreen)
                }

                is ViewModelToLoginScreenEvents.InvalidResponseError -> {
                    loginViewModelToLoginScreenEvents.send(
                        ViewModelToLoginScreenEvents.InvalidResponseError(
                            event.reason
                        )
                    )
                }
            }
        }
    }


    private fun setUserAuth(value: String) {
        viewModelScope.launch { localData.setUserAuth(value) }
    }
}

sealed class ViewModelToLoginScreenEvents {
    data object NavigateToHomeScreen : ViewModelToLoginScreenEvents()
    data class InvalidResponseError(val reason: String) : ViewModelToLoginScreenEvents()
}