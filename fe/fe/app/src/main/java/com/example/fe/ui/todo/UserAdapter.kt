package com.example.fe.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.User
import com.example.fe.databinding.ItemUserBinding
import com.example.fe.user

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
        val targetUser = users[position]
        holder.binding.apply {
            txtName.text = targetUser.name
            
            val currentUserRole = user?.role?.lowercase() ?: ""
            val targetUserRole = targetUser.role.lowercase()

            // Logic: 
            // - If Admin is looking, show Chat for Students.
            // - If Student is looking, show Chat for Admins.
            val canChat = (currentUserRole == "admin" && targetUserRole == "student") ||
                         (currentUserRole == "student" && targetUserRole == "admin")

            btnChat.visibility = if (canChat) View.VISIBLE else View.GONE

            btnChat.setOnClickListener { onChatClick(targetUser) }
            btnDetail.setOnClickListener { onDetailClick(targetUser) }
            
            // Hide detail button if it's admin list for support
            btnDetail.visibility = if (currentUserRole == "student") View.GONE else View.VISIBLE
        }
    }

    override fun getItemCount(): Int = users.size

    fun updateData(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}