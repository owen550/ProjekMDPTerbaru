package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentUserDetailBinding

class UserDetailFragment : Fragment() {
    lateinit var binding: FragmentUserDetailBinding
    private val viewModel: TodosViewModel by viewModels { TodoViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("userId") ?: 0

        if (userId != 0) {
            viewModel.fetchUserDetail(userId)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.oneuser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.apply {
                    tvNameLarge.text = it.name
                    tvUsernameValue.text = it.username ?: "-"
                    tvEmailValue.text = it.email
                    tvBirthdayValue.text = it.birthday_date ?: "-"
                    tvGoogleIdValue.text = it.google_id ?: "-"
                    tvRoleValue.text = it.role
                    tvTierValue.text = it.tier
                    tvPointsValue.text = it.points.toString()
                }
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (msg.isNotEmpty()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}