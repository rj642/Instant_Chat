package com.softocorp.instantchat.chat.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softocorp.instantchat.chat.ui.interfaces.ClickHandler
import com.softocorp.instantchat.chat.model.ChatModel
import com.softocorp.instantchat.databinding.FragmentChatBinding

class ChatFragment : Fragment(), ClickHandler {

    private lateinit var binding: FragmentChatBinding

    private val chatList = mutableListOf<ChatModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)

        binding.apply {

        }

        return binding.root
    }

    override fun onClick(item: Any, position: Int) {

    }

    override fun onLongPressed(item: List<Any>, position: List<Int>) {

    }

    override fun deleteSelectedItems(item: List<Any>) {

    }
}