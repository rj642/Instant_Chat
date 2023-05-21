package com.softocorp.instantchat.messages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.softocorp.instantchat.chat.model.ChatModel
import com.softocorp.instantchat.databinding.MessageActionBottomSheetBinding
import com.softocorp.instantchat.databinding.ReceiverMessageViewBinding
import com.softocorp.instantchat.databinding.SenderMessageViewBinding
import com.softocorp.instantchat.messages.interfaces.MessageListener
import com.softocorp.instantchat.utils.Extensions.createBottomSheet
import com.softocorp.instantchat.utils.Extensions.showBottomSheet

class MessageAdapter(
    var list: List<ChatModel>,
    var currentUid: String,
    var listener: MessageListener
): ListAdapter<ChatModel, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    companion object {
        const val MESSAGE_SENDER = 0
        const val MESSAGE_RECEIVER = 1
    }

    inner class SenderViewHolder(private val binding: SenderMessageViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatModel, position: Int) {
            binding.apply {
                senderContent.text = item.lastSentMessage
                root.setOnLongClickListener {
                    val dialogBinding = MessageActionBottomSheetBinding.inflate(LayoutInflater.from(it.context))
                    val bottomSheet = it.context.createBottomSheet(dialogBinding.root)
                    dialogBinding.apply {
                        actionDelete.setOnClickListener {
                            listener.deleteMessage(item, position)
                        }
                    }
                    it.context.showBottomSheet(bottomSheet)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    inner class ReceiverViewHolder(private val binding: ReceiverMessageViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatModel, position: Int) {
            binding.apply {
                root.setOnLongClickListener {
                    val dialogBinding = MessageActionBottomSheetBinding.inflate(LayoutInflater.from(it.context))
                    val bottomSheet = it.context.createBottomSheet(dialogBinding.root)
                    dialogBinding.apply {
                        actionDelete.setOnClickListener {
                            listener.deleteMessage(item, position)
                        }
                    }
                    it.context.showBottomSheet(bottomSheet)
                    return@setOnLongClickListener true
                }
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
                (holder as SenderViewHolder).bind(item, position)
            }
            MESSAGE_RECEIVER -> {
                (holder as ReceiverViewHolder).bind(item, position)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class DiffUtilCallBack: DiffUtil.ItemCallback<ChatModel>() {
        override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel) =
            oldItem == newItem
    }

}