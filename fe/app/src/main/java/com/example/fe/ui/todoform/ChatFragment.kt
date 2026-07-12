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
    lateinit var binding: FragmentChatBinding
    private val viewModel: TodosViewModel by viewModels { TodoViewModelFactory }
    private lateinit var chatAdapter: ChatAdapter

    private var adminId: Int = 0
    private var userId: Int = 0
    private var userRole: String = "student" // Default

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data dari arguments (navigasi)
        adminId = arguments?.getInt("adminId") ?: 0
        userId = arguments?.getInt("userId") ?: 0
        // Tentukan role untuk styling & logic bubble
        userRole = if (com.example.fe.user?.role == "admin") "admin" else "student"

        setupTheme()
        setupRecyclerView()
        setupListeners()
        observeViewModel()

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
                    viewModel.sendMessageFromAdmin(adminId, userId, "Admin Message", text)
                } else {
                    viewModel.sendMessageFromUser(userId, adminId, "User Message", text)
                }
                binding.etMessage.text.clear()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.messages.observe(viewLifecycleOwner) { list ->
            chatAdapter.submitList(list)
            if (list.isNotEmpty()) {
                binding.rvChat.smoothScrollToPosition(list.size - 1)
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
                viewModel.getMessagesById(userId, adminId)
                delay(3000) // Poll setiap 3 detik
            }
        }
    }
}
