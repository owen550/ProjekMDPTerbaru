package com.example.frontend_bsua.ui.todoform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.frontend_bsua.R
import com.example.frontend_bsua.TodoViewModelFactory
import com.example.frontend_bsua.databinding.FragmentAddJadwalBinding
import com.example.frontend_bsua.databinding.FragmentShowBinding
import kotlin.getValue

class AddJadwal : Fragment() {
    lateinit var binding: FragmentAddJadwalBinding
    val viewModelAddJadwal by viewModels<AddJadwalViewModel> { TodoViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddJadwalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserve()
        onListern()
    }

    fun onObserve() {
        viewModelAddJadwal.message.observe(viewLifecycleOwner) { msg ->
            if (msg != "") {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                viewModelAddJadwal.reset()
            }
        }
        viewModelAddJadwal.loading.observe(viewLifecycleOwner) { lod ->
            if (lod) {
                binding.putBtnTodoform.setText("waiting...")
                binding.cancelBtnTodoform.setText("waiting...")
                binding.putBtnTodoform.isEnabled = false
                binding.cancelBtnTodoform.isEnabled = false
            } else {
                binding.putBtnTodoform.setText("Simpan")
                binding.cancelBtnTodoform.setText("Batal")
                binding.putBtnTodoform.isEnabled = true
                binding.cancelBtnTodoform.isEnabled = true
                binding.contentEtTodoform.setText("")
            }
        }
    }

    fun onListern() {
        binding.putBtnTodoform.setOnClickListener {
            var waktusekarang = System.currentTimeMillis()
            var title = binding.contentEtTodoform.text.toString()
            viewModelAddJadwal.insertData(title, waktusekarang)
        }
        binding.cancelBtnTodoform.setOnClickListener {
            findNavController().navigate(R.id.FragmenShow)
        }
    }
}