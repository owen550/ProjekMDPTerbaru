package com.example.fe.ui.todo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.LoginPage
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.data.User
import com.example.fe.databinding.FragmentAdminUserListBinding
import com.example.fe.ui.todoform.currentUserId
import com.example.fe.user

class AdminUserListFragment : Fragment() {
    lateinit var binding: FragmentAdminUserListBinding
    private val viewModel: TodosViewModel by viewModels { TodoViewModelFactory }
    private lateinit var userAdapter: UserAdapter
    private var allUsers: List<User> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        observeViewModel()

        viewModel.fetchAllUsers()
        viewModel.getOneUserByID()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(
            users = emptyList(),
            onChatClick = { targetUser ->
                val bundle = Bundle().apply {
                    putInt("adminId", currentUserId ?: 0)
                    putInt("userId", targetUser.id ?: 0)
                }
                findNavController().navigate(R.id.action_adminUserListFragment_to_chatFragment, bundle)
            },
            onDetailClick = { targetUser ->
                val bundle = Bundle().apply {
                    putInt("userId", targetUser.id ?: 0)
                }
                findNavController().navigate(R.id.action_adminUserListFragment_to_userDetailFragment, bundle)
            }
        )
        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }

    private fun setupListeners() {
        binding.btnSearch.setOnClickListener {
            filterData()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterData()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnTeacher.setOnClickListener {
            filterByRole("teacher")
        }

        binding.btnStudent.setOnClickListener {
            filterByRole("student")
        }

        binding.imgProfile.setOnClickListener {
            // Logout
            user = null
            currentUserId = null
            val intent = Intent(requireContext(), LoginPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            allUsers = users
            userAdapter.updateData(users)
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

    private fun filterData() {
        val query = binding.etSearch.text.toString().lowercase()
        val filteredList = allUsers.filter {
            it.name.lowercase().contains(query) || (it.username?.lowercase()?.contains(query) == true)
        }
        userAdapter.updateData(filteredList)
    }

    private fun filterByRole(role: String) {
        val filteredList = allUsers.filter { it.role.equals(role, ignoreCase = true) }
        userAdapter.updateData(filteredList)
    }
}
