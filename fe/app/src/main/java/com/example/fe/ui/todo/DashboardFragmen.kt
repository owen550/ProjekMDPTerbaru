package com.example.fe.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentDashboardBinding

class DashboardFragmen : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private val viewModel: TodosViewModel by viewModels {
        TodoViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onObserve()
        onListen()

        // Tes ambil user
        viewModel.getOneUserByID()
    }

    private fun onObserve() {

        viewModel.oneuser.observe(viewLifecycleOwner) { user ->
            binding.etTitle.setText(user.name)
        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if(msg != "" && msg != null){
                Toast.makeText(requireContext(),msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onListen() {

    }
}