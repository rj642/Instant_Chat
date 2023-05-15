package com.softocorp.instantchat.chat.ui.interfaces

interface ClickHandler {

    /**
     * Implement this method inside activity
     */
    fun onClick(item: Any, position: Int)

    /**
     * Implement long pressed listener as well
     */
    fun onLongPressed(item: List<Any>, position: List<Int>)

    /**
     * Implement delete function
     */
    fun deleteSelectedItems(item: List<Any>)

}