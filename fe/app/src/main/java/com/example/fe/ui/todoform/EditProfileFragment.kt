package com.example.fe.ui.todoform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentChatBinding
import com.example.fe.databinding.FragmentEditProfileBinding
import com.example.fe.ui.todo.TodosViewModel
import com.example.fe.user
import kotlin.getValue

class EditProfileFragment : Fragment() {

    lateinit var binding: FragmentEditProfileBinding
    private val viewModel: TodoFormViewModel by viewModels {
        TodoViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // === isi info user ===
        setIsianUpdate()

        // === view model ===
        onListener()
        onObserve()

    }

    fun setIsianUpdate(){
        binding.etEditName.setText(user!!.name)
        binding.etEditUsername.setText(user!!.username)
    }

    fun onListener(){
        // back
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.profileFragmen)
        }

        // edit profil
        binding.btnSaveChanges.setOnClickListener {
            val name = binding.etEditName.text.toString().trim()
            val username = binding.etEditUsername.text.toString().trim()

            if (name.isBlank()) {
                binding.etEditName.error = "Nama tidak boleh kosong"
                binding.etEditName.requestFocus()
                return@setOnClickListener
            } else if (username.isBlank()) {
                binding.etEditUsername.error = "Username tidak boleh kosong"
                binding.etEditUsername.requestFocus()
                return@setOnClickListener
            }
            // kalau sampai sini berarti lolos, update data user
            viewModel.updateUser(name,username)

            // navigate ke profile
            findNavController().navigate(R.id.profileFragmen)
        }
    }
    fun onObserve(){
        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrEmpty() && msg != "") {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}