package com.softocorp.instantchat.messages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.softocorp.instantchat.chat.model.ChatModel
import com.softocorp.instantchat.databinding.ReceiverMessageViewBinding
import com.softocorp.instantchat.databinding.SenderMessageViewBinding
import com.softocorp.instantchat.messages.interfaces.MessageListener

class MessageAdapter(
    var list: List<ChatModel>,
    var currentUid: String,
    var listener: MessageListener
): RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val MESSAGE_SENDER = 0
        const val MESSAGE_RECEIVER = 1
    }

    inner class SenderViewHolder(private val binding: SenderMessageViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatModel) {
            binding.apply {
                senderContent.text = item.lastSentMessage
            }
        }
    }

    inner class ReceiverViewHolder(private val binding: ReceiverMessageViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatModel) {
            binding.apply {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            MESSAGE_SENDER -> {
                SenderViewHolder(SenderMessageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            MESSAGE_RECEIVER -> {
                ReceiverViewHolder(ReceiverMessageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                SenderViewHolder(SenderMessageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position].senderData.user_id) {
            currentUid -> {
                MESSAGE_SENDER
            }
            else -> {
                MESSAGE_RECEIVER
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        when (holder.itemViewType) {
            MESSAGE_SENDER -> {
                (holder as SenderViewHolder).bind(item)
            }
            MESSAGE_RECEIVER -> {
                (holder as ReceiverViewHolder).bind(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}