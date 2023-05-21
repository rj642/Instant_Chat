package com.softocorp.instantchat.messages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.softocorp.instantchat.R
import com.softocorp.instantchat.chat.model.ChatModel
import com.softocorp.instantchat.databinding.MessageActionBottomSheetBinding
import com.softocorp.instantchat.databinding.ReceiverMessageViewBinding
import com.softocorp.instantchat.databinding.SenderMessageViewBinding
import com.softocorp.instantchat.messages.interfaces.MessageListener
import com.softocorp.instantchat.utils.Extensions.createBottomSheet
import com.softocorp.instantchat.utils.Extensions.loadLocalImage
import com.softocorp.instantchat.utils.Extensions.showBottomSheet

class MessageAdapter(
    var list: List<ChatModel>,
    var currentUid: String,
    var listener: MessageListener
) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val MESSAGE_SENDER = 0
        const val MESSAGE_RECEIVER = 1
    }

    inner class SenderViewHolder(private val binding: SenderMessageViewBinding) :
        ViewHolder(binding.root) {
        fun bind(item: ChatModel, position: Int) {
            binding.apply {
                senderContent.text = item.lastSentMessage
                userImage.loadLocalImage(R.drawable.cat_one)
                root.setOnLongClickListener {
                    val dialogBinding =
                        MessageActionBottomSheetBinding.inflate(LayoutInflater.from(it.context))
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

    inner class ReceiverViewHolder(private val binding: ReceiverMessageViewBinding) :
        ViewHolder(binding.root) {
        fun bind(item: ChatModel, position: Int) {
            binding.apply {
                userImage.loadLocalImage(R.drawable.cat_two)
                root.setOnLongClickListener {
                    val dialogBinding =
                        MessageActionBottomSheetBinding.inflate(LayoutInflater.from(it.context))
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
                SenderViewHolder(
                    SenderMessageViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            MESSAGE_RECEIVER -> {
                ReceiverViewHolder(
                    ReceiverMessageViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                SenderViewHolder(
                    SenderMessageViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
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

}

/**
 * TODO: Will work on it later on
 */
class DiffUtilCallBack : DiffUtil.ItemCallback<ChatModel>() {
    override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel) =
        oldItem == newItem
}