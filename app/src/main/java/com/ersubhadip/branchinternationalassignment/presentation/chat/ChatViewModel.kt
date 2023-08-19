package com.ersubhadip.branchinternationalassignment.presentation.chat

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
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository, private val localData: Session
) : ViewModel() {

    val chatViewModelToChatScreenEvents = Channel<ViewModelToChatScreenEvents>()

    private val _replyState = mutableStateOf("")
    private val _idState = mutableStateOf(-1)

    val replyState: State<String> = _replyState
    val idState: State<Int> = _idState


    fun sendReply() {
        viewModelScope.launch {
            if (_idState.value != -1 && _replyState.value.isNotEmpty()) {
                repository.sendChat(
                    auth = localData.getAuthToken() ?: "",
                    id = _idState.value,
                    message = _replyState.value
                ).collectLatest { resp ->
                    if (resp.success) {
                        emitChatScreenEvents(ViewModelToChatScreenEvents.Success)
                    } else {
                        emitChatScreenEvents(
                            ViewModelToChatScreenEvents.Failure("Something went wrong!")
                        )
                    }
                }
            } else {
                emitChatScreenEvents(
                    ViewModelToChatScreenEvents.Failure("Something went wrong!")
                )
            }
        }
    }

    fun onReplyChange(newStr: String) {
        _replyState.value = newStr.trim()
    }

    fun setId(id: Int) {
        _idState.value = id
    }


    private fun emitChatScreenEvents(event: ViewModelToChatScreenEvents) {
        viewModelScope.launch {
            when (event) {
                ViewModelToChatScreenEvents.Success -> {
                    chatViewModelToChatScreenEvents.send(ViewModelToChatScreenEvents.Success)
                }

                is ViewModelToChatScreenEvents.Failure -> {
                    chatViewModelToChatScreenEvents.send(
                        ViewModelToChatScreenEvents.Failure(event.errorMessage)
                    )
                }
            }
        }
    }
}

sealed class ViewModelToChatScreenEvents {
    data object Success : ViewModelToChatScreenEvents()
    data class Failure(val errorMessage: String) : ViewModelToChatScreenEvents()
}