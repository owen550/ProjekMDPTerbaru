package com.example.fe.ui.todo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.LoginPage
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentAdminLogBinding
import com.example.fe.ui.todoform.currentUserId
import com.example.fe.user

class AdminLogFragment : Fragment() {
    private var _binding: FragmentAdminLogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodosViewModel by viewModels { TodoViewModelFactory }
    private lateinit var logAdapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupListeners()

        viewModel.fetchAllActivityLogs()
        viewModel.getOneUserByID()
    }

    private fun setupRecyclerView() {
        logAdapter = LogAdapter { userId ->
            val bundle = bundleOf("userId" to userId)
            findNavController().navigate(R.id.action_adminLogFragment_to_userDetailFragment, bundle)
        }
        binding.rvLogs.apply {
            adapter = logAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupListeners() {
        binding.imgProfile.setOnClickListener {
            // Logout logic
            user = null
            currentUserId = null
            val intent = Intent(requireContext(), LoginPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.activityLogs.observe(viewLifecycleOwner) { logs ->
            logAdapter.submitList(logs)
        }

        viewModel.oneuser.observe(viewLifecycleOwner) { user ->
            binding.tvAdminName.text = user?.name ?: "Admin"
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            // Handle loading state
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty() && message != "User tidak ketemu" && !message.contains("Admin")) {
                // Avoid showing name toast if it's just the name set in getOneUserByID
                // (Though getOneUserByID in ViewModel sets _message.value = user.name)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
