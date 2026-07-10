package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.data.User
import com.example.fe.databinding.FragmentAdminListBinding
import com.example.fe.ui.todoform.currentUserId

class AdminListFragment : Fragment() {
    private var _binding: FragmentAdminListBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TodosViewModel by viewModels { TodoViewModelFactory }
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        viewModel.fetchAllUsers()
        viewModel.getOneUserByID()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(
            users = emptyList(),
            onChatClick = { admin ->
                val bundle = Bundle().apply {
                    putInt("adminId", admin.id ?: 0)
                    putInt("userId", currentUserId ?: 0)
                }
                // Fix: Navigate to the correct human chat fragment
                findNavController().navigate(R.id.action_adminListFragment_to_chatFragment, bundle)
            },
            onDetailClick = { /* No detail for admin list usually */ }
        )
        binding.rvAdminList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@AdminListFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(viewLifecycleOwner) { allUsers ->
            // Filter only admins
            val admins = allUsers.filter { it.role.lowercase() == "admin" }
            adapter.updateData(admins)
        }

        viewModel.oneuser.observe(viewLifecycleOwner) { user ->
            binding.tvAdminName.text = user?.name ?: "Admin"
        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (msg.isNotEmpty() && !msg.contains("Admin") && msg != "User tidak ketemu") {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
