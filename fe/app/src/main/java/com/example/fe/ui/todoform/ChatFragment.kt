package com.example.fe.ui.todoform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fe.R
import com.example.fe.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    // Menggunakan lateinit var binding sesuai referensi QuizzEsayDanProyekFragmen
    lateinit var binding: FragmentChatBinding

    // Template Logic: True = Admin (Hijau), False = User (Oranye)
    private var isAdmin: Boolean = true 

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupTheme()
        setupListeners()
    }

    private fun setupTheme() {
        if (isAdmin) {
            // Tema Admin (Hijau)
            binding.chatContainer.setBackgroundResource(R.drawable.bg_chat_container_admin)
            binding.btnStopServer.visibility = View.VISIBLE
            binding.btnStopServer.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.admin_green_primary)
            binding.btnSend.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.admin_green_primary)
        } else {
            // Tema User (Oranye)
            binding.chatContainer.setBackgroundResource(R.drawable.bg_chat_container_user)
            binding.btnStopServer.visibility = View.GONE
            binding.btnSend.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.user_orange_primary)
        }
    }

    private fun setupListeners() {
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            if (message.isNotBlank()) {
                // TODO: Logic kirim pesan
                binding.etMessage.text.clear()
            }
        }

        binding.btnStopServer.setOnClickListener {
            // TODO: Logic stop server
        }
    }
}