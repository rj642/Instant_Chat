package com.softocorp.instantchat.messages.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.softocorp.instantchat.R
import com.softocorp.instantchat.chat.model.ChatModel
import com.softocorp.instantchat.chat.model.MessageData
import com.softocorp.instantchat.chat.model.ProfileData
import com.softocorp.instantchat.databinding.ActivityMessageBinding
import com.softocorp.instantchat.messages.adapter.MessageAdapter
import com.softocorp.instantchat.messages.interfaces.MessageListener
import com.softocorp.instantchat.utils.Extensions.scrollRecycler
import com.softocorp.instantchat.utils.Extensions.setUpToolbar

class MessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding

    private lateinit var adapter: MessageAdapter

    companion object {
        val pd1 = ProfileData(user_id = "abc1234", "Rahul Jha")
        val pd2 = ProfileData(user_id = "abc1432", "Test 001")
        val chatData = mutableListOf<ChatModel>()
        val messageData = mutableListOf<MessageData>()
    }

    init {
        for (i in 0..24) {
            messageData.add(MessageData())
        }
        for (i in 0..100) {
            chatData.add(ChatModel(if (i % 3 == 0) pd1 else pd2, if (i % 4 != 0) pd2 else pd1, messageData, ))
        }
    }

    private var listener = object: MessageListener {
        override fun addMessage() {
            chatData.add(ChatModel(pd1, receiverData = pd2, lastSentMessage = if (!TextUtils.isEmpty(binding.messageContent.text.toString().trim())) binding.messageContent.text.toString().trim() else "This is another message :)"))
            if (this@MessageActivity::binding.isInitialized) {
                binding.messageRecyclerView.adapter?.notifyItemInserted(chatData.size)
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.messageRecyclerView.scrollToPosition(chatData.size - 1)
                }, 400L)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MessageAdapter(
            chatData,
            "abc1234",
            listener
        )

        binding.apply {
            messageRecyclerView.adapter = adapter
            messageRecyclerView.scrollRecycler(chatData.size - 1)
            val toolbarContent = setUpToolbar(toolbar)
            btnSend.setOnClickListener {
                listener.addMessage()
                messageContent.setText("")
            }
        }

    }
}