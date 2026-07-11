package com.example.fe.ui.todoform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentChatBinding
import com.example.fe.ui.todo.TodosViewModel

class ChatbotFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    private val viewModel: TodosViewModel by viewModels {
        TodoViewModelFactory
    }

    private lateinit var chatAdapter: ChatbotAdapter

    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Current logged in user
        userId = com.example.fe.user?.id ?: 0

        setupTheme()
        setupRecyclerView()
        setupListeners()
        observeViewModel()

        // Load previous conversation
        viewModel.getChatbotMessages(userId)
    }

    private fun setupTheme() {

        binding.chatContainer.setBackgroundResource(
            R.drawable.bg_chat_container_user
        )

        binding.btnSend.backgroundTintList =
            ContextCompat.getColorStateList(
                requireContext(),
                R.color.user_orange_primary
            )

    }

    private fun setupRecyclerView() {

        chatAdapter = ChatbotAdapter()

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

            if (text.isEmpty()) {
                return@setOnClickListener
            }

            viewModel.sendAiMessage(
                userId = userId,
                role = "student",
                message = text
            )

            binding.etMessage.text.clear()

        }

    }

    private fun observeViewModel() {

        viewModel.chatbotMessages.observe(viewLifecycleOwner) { chats ->

            chatAdapter.submitList(chats)

            if (chats.isNotEmpty()) {
                binding.rvChat.smoothScrollToPosition(
                    chats.size - 1
                )
            }

        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->

            if (msg.isNotEmpty()) {

                Toast.makeText(
                    requireContext(),
                    msg,
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

    }

}