package com.ersubhadip.branchinternationalassignment.presentation.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ersubhadip.branchinternationalassignment.data.local.Session
import com.ersubhadip.branchinternationalassignment.data.pojos.ChatItem
import com.ersubhadip.branchinternationalassignment.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ChatRepository, private val localData: Session
) : ViewModel() {

    val homeViewModelToHomeScreenEvents = Channel<ViewModelToHomeScreenEvents>()

    private var _chats = MutableStateFlow<List<ChatItem>>(emptyList())
    val chats: StateFlow<List<ChatItem>> get() = _chats

    init {
        viewModelScope.launch {
            getChats()
        }
    }

    private suspend fun getChats() {
        viewModelScope.launch {
            repository.getChats(localData.getAuthToken() ?: "").collectLatest { response ->
                if (response.success && response.payload != null) {
                    _chats.emit(response.payload)
                    emitHomeScreenEvents(ViewModelToHomeScreenEvents.Success)
                } else {
                    emitHomeScreenEvents(ViewModelToHomeScreenEvents.Failure)
                }
            }
        }
    }

    private fun emitHomeScreenEvents(event: ViewModelToHomeScreenEvents) {
        viewModelScope.launch {
            when (event) {
                is ViewModelToHomeScreenEvents.Success -> {
                    homeViewModelToHomeScreenEvents.send(ViewModelToHomeScreenEvents.Success)
                }

                is ViewModelToHomeScreenEvents.Failure -> {
                    homeViewModelToHomeScreenEvents.send(ViewModelToHomeScreenEvents.Failure)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(s: String?): String {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
        val dateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(s)
        return dateFormat.format(dateTime!!)
    }
}

sealed class ViewModelToHomeScreenEvents {
    data object Success : ViewModelToHomeScreenEvents()
    data object Failure : ViewModelToHomeScreenEvents()
}