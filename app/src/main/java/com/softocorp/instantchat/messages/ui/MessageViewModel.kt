package com.softocorp.instantchat.messages.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softocorp.instantchat.chat.model.ChatModel

class MessageViewModel: ViewModel() {

    private val _messageData = MutableLiveData<List<ChatModel>>()

    val messageData
        get() = _messageData

    init {
        _messageData.value = emptyList()
    }

}