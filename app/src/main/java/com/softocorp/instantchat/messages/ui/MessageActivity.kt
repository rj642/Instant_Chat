package com.softocorp.instantchat.messages.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.*
import com.softocorp.instantchat.chat.model.ChatModel
import com.softocorp.instantchat.chat.model.MessageData
import com.softocorp.instantchat.chat.model.ProfileData
import com.softocorp.instantchat.databinding.ActivityMessageBinding
import com.softocorp.instantchat.messages.adapter.MessageAdapter
import com.softocorp.instantchat.messages.interfaces.MessageListener
import com.softocorp.instantchat.utils.Extensions.hideSoftKeyboard
import com.softocorp.instantchat.utils.Extensions.scrollRecycler
import com.softocorp.instantchat.utils.Extensions.setUpToolbar
import com.softocorp.instantchat.utils.Extensions.setSnackBar

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

        var orgItem: ChatModel? = null
        var orgPosition: Int = 0

        override fun addMessage() {
            chatData.add(ChatModel(pd1, receiverData = pd2, lastSentMessage = if (!TextUtils.isEmpty(binding.messageContent.text.toString().trim())) binding.messageContent.text.toString().trim() else "This is another message :)"))
            if (this@MessageActivity::binding.isInitialized) {
                adapter.notifyItemInserted(chatData.size)
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.messageRecyclerView.smoothScrollToPosition(chatData.size - 1)
                }, 400L)
            }
        }

        override fun deleteMessage(item: ChatModel, position: Int) {
            // Extra check to perform exact removal of the selected entry
            val found: Boolean = chatData.contains(item)
            orgItem = item
            orgPosition = position
            if (found && (chatData.indexOf(item) == position)) {
                chatData.remove(item)
                if (this@MessageActivity::binding.isInitialized) {
                    adapter.notifyItemRemoved(position)
                    binding.root.setSnackBar("Message deleted successfully", true, "Undo") {
                        restoreMessage()
                        adapter.notifyItemInserted(position)
                        binding.messageRecyclerView.smoothScrollToPosition(position)
                        binding.root.setSnackBar("Message restored successfully")
                    }
                }
            } else {
                if (this@MessageActivity::binding.isInitialized) {
                    binding.root.setSnackBar("Message can't be deleted")
                }
            }
        }

        override fun restoreMessage() {
            orgItem?.let { item ->
                chatData.add(orgPosition, item)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MessageAdapter(
            chatData,
            currentUid = "abc1234",
            listener
        )

        binding.apply {
            messageRecyclerView.adapter = adapter
            messageRecyclerView.scrollRecycler(chatData.size - 1)
            val toolbarContent = setUpToolbar(toolbar, showBackButton = true)
            toolbarContent.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            btnSend.setOnClickListener {
                listener.addMessage()
                messageContent.setText("".trim())
                this@MessageActivity.hideSoftKeyboard()
            }

            messageContent.setOnEditorActionListener { _, _, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    listener.addMessage()
                    binding.messageContent.setText("".trim())
                    this@MessageActivity.hideSoftKeyboard()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            this@MessageActivity.hideSoftKeyboard()
        }
        return super.dispatchTouchEvent(ev)
    }

}