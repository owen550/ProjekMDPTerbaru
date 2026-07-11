package com.example.fe.ui.todoform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.AdminMessage
import com.example.fe.databinding.ItemChatLeftBinding
import com.example.fe.databinding.ItemChatRightBinding

class ChatAdapter(private val userRole: String) : ListAdapter<AdminMessage, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        val isFromAdmin = message.message_title.contains("Admin", ignoreCase = true)
        
        return if (userRole == "admin") {
            if (isFromAdmin) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
        } else {
            if (isFromAdmin) VIEW_TYPE_RECEIVED else VIEW_TYPE_SENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val binding = ItemChatRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SentViewHolder(binding)
        } else {
            val binding = ItemChatLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceivedViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is SentViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedViewHolder) {
            holder.bind(message)
        }
    }

    inner class SentViewHolder(private val binding: ItemChatRightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: AdminMessage) {
            binding.tvMessageRight.text = message.message_body
            binding.tvDateRight.text = message.created_at
        }
    }

    inner class ReceivedViewHolder(private val binding: ItemChatLeftBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: AdminMessage) {
            binding.tvMessageLeft.text = message.message_body
            binding.tvDateLeft.text = message.created_at
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AdminMessage>() {
        override fun areItemsTheSame(oldItem: AdminMessage, newItem: AdminMessage): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: AdminMessage, newItem: AdminMessage): Boolean = oldItem == newItem
    }
}
