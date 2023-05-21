package com.softocorp.instantchat.chat.ui.interfaces

import com.softocorp.instantchat.chat.model.ChatModel

interface ClickHandler {

    /**
     * Implement this method inside activity
     */
    fun onClick(item: ChatModel, position: Int)

    /**
     * Implement long pressed listener as well
     */
    fun onLongPressed(item: List<ChatModel>, position: List<Int>)

    /**
     * Implement delete function
     */
    fun deleteSelectedItems(item: List<ChatModel>)

}