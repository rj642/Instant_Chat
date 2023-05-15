package com.softocorp.instantchat.chat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softocorp.instantchat.chat.ui.interfaces.ClickHandler
import com.softocorp.instantchat.databinding.ChatRecyclerViewBinding

class ChatRecyclerAdapter(
    var list: List<Any>,
    var listener: ClickHandler
): RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder>() {



    inner class ChatViewHolder(private val binding: ChatRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(ChatRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = list[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}