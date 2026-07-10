package com.example.fe.ui.todoform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentChatBinding
import com.example.fe.ui.todo.TodosViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TodosViewModel by viewModels { TodoViewModelFactory }
    private lateinit var chatAdapter: ChatAdapter
    
    private var adminId: Int = 0
    private var userId: Int = 0 // Refers to the student/user ID
    private var userRole: String = "student"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        adminId = arguments?.getInt("adminId") ?: 0
        userId = arguments?.getInt("userId") ?: 0
        userRole = if (com.example.fe.user?.role == "admin") "admin" else "student"

        setupTheme()
        setupRecyclerView()
        setupListeners()
        observeViewModel()
        
        // Clear old messages before polling to prevent "bleed" from previous session
        viewModel.reset() 
        startPolling()
    }

    private fun setupTheme() {
        if (userRole == "admin") {
            binding.chatContainer.setBackgroundResource(R.drawable.bg_chat_container_admin)
            binding.btnSend.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.admin_green_primary)
        } else {
            binding.chatContainer.setBackgroundResource(R.drawable.bg_chat_container_user)
            binding.btnSend.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.user_orange_primary)
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(userRole)
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSend.setOnClickListener {
            val text = binding.etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                if (userRole == "admin") {
                    // Current user is Admin (adminId), sending to Student (userId)
                    viewModel.sendMessageFromAdmin(adminId, userId, "Admin Message", text)
                } else {
                    // Current user is Student (userId), sending to Admin (adminId)
                    viewModel.sendMessageFromUser(userId, adminId, "User Message", text)
                }
                binding.etMessage.text.clear()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.messages.observe(viewLifecycleOwner) { list ->
            // Filter messages to strictly match this specific Admin-Student conversation
            val filteredList = list.filter { 
                it.admin_id == adminId && it.receiver_id == userId
            }
            chatAdapter.submitList(filteredList)
            if (filteredList.isNotEmpty()) {
                binding.rvChat.smoothScrollToPosition(filteredList.size - 1)
            }
        }
        
        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (msg.isNotEmpty() && msg.contains("Gagal")) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startPolling() {
        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                if (userId != 0 && adminId != 0) {
                    viewModel.getMessagesById(userId, adminId)
                }
                delay(3000)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
