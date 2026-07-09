package com.example.fe.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.User
import com.example.fe.databinding.ItemUserBinding

class UserAdapter(
    private var users: List<User>,
    private val onChatClick: (User) -> Unit,
    private val onDetailClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.binding.apply {
            txtName.text = user.name
            
            // Only show chat button for students
            btnChat.visibility = if (user.role.lowercase() == "student") View.VISIBLE else View.GONE

            btnChat.setOnClickListener { onChatClick(user) }
            btnDetail.setOnClickListener { onDetailClick(user) }
        }
    }

    override fun getItemCount(): Int = users.size

    fun updateData(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}