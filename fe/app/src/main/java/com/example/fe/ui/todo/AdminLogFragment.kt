package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentAdminLogBinding

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

        // Fetch logs for admin (assuming admin ID is 1 for now or handled by session)
        // You might want to get the actual user ID from your session manager
        viewModel.fetchAllActivityLogs()

        binding.btnStopServer.setOnClickListener {
            Toast.makeText(requireContext(), "Stopping server...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        logAdapter = LogAdapter()
        binding.rvLogs.apply {
            adapter = logAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.activityLogs.observe(viewLifecycleOwner) { logs ->
            logAdapter.submitList(logs)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            // Handle loading state if you have a progress bar
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty() && message != "User tidak ketemu") {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}