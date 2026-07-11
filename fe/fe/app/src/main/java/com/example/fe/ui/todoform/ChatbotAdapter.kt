package com.example.fe.ui.todoform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.CsChatbotChat
import com.example.fe.databinding.ItemChatLeftBinding
import com.example.fe.databinding.ItemChatRightBinding

class ChatbotAdapter :
    ListAdapter<CsChatbotChat, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).sender == "user") {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_SENT) {

            val binding = ItemChatRightBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            SentViewHolder(binding)

        } else {

            val binding = ItemChatLeftBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            ReceivedViewHolder(binding)

        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        val chat = getItem(position)

        when (holder) {

            is SentViewHolder -> holder.bind(chat)

            is ReceivedViewHolder -> holder.bind(chat)

        }

    }

    inner class SentViewHolder(
        private val binding: ItemChatRightBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: CsChatbotChat) {

            binding.tvMessageRight.text = chat.message
            binding.tvDateRight.text = chat.created_at

        }

    }

    inner class ReceivedViewHolder(
        private val binding: ItemChatLeftBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: CsChatbotChat) {

            binding.tvMessageLeft.text = chat.message
            binding.tvDateLeft.text = chat.created_at

        }

    }

    class DiffCallback : DiffUtil.ItemCallback<CsChatbotChat>() {

        override fun areItemsTheSame(
            oldItem: CsChatbotChat,
            newItem: CsChatbotChat
        ): Boolean {

            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(
            oldItem: CsChatbotChat,
            newItem: CsChatbotChat
        ): Boolean {

            return oldItem == newItem

        }

    }

}