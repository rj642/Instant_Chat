package com.softocorp.instantchat.messages.interfaces

import com.softocorp.instantchat.chat.model.ChatModel

interface MessageListener {
    fun addMessage()

    fun deleteMessage(item: ChatModel, position: Int)

    fun restoreMessage()

}