package com.softocorp.instantchat.chat.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.softocorp.instantchat.chat.ui.interfaces.ClickHandler
import com.softocorp.instantchat.chat.model.ChatModel
import com.softocorp.instantchat.chat.model.MessageData
import com.softocorp.instantchat.chat.ui.adapter.ChatRecyclerAdapter
import com.softocorp.instantchat.databinding.FragmentChatBinding
import com.softocorp.instantchat.messages.ui.MessageActivity

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    private val chatList = mutableListOf<ChatModel>()

    private lateinit var adapter: ChatRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val clickHandler = object: ClickHandler {
        override fun onClick(item: ChatModel, position: Int) {
            startActivity(Intent(requireActivity().applicationContext, MessageActivity::class.java)
                .putParcelableArrayListExtra("message_data", arrayListOf(item)))
        }

        override fun onLongPressed(item: List<ChatModel>, position: List<Int>) {
            TODO("Not yet implemented")
        }

        override fun deleteSelectedItems(item: List<ChatModel>) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..24) {
            MessageActivity.messageData.add(MessageData())
        }
        for (i in 0..100) {
            MessageActivity.chatData.add(ChatModel(senderData = if (i % 3 == 0) MessageActivity.pd1 else MessageActivity.pd2, receiverData = if (i % 4 != 0) MessageActivity.pd2 else MessageActivity.pd1,
                message = MessageActivity.messageData))
        }

        binding.apply {
            adapter = ChatRecyclerAdapter(MessageActivity.chatData, clickHandler)
            chatRecyclerView.adapter = adapter
            chatRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

    }

}