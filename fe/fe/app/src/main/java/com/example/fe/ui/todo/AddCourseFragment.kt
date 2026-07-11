package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentAddCourseBinding
import com.example.fe.ui.todoform.TodoFormViewModel
import com.example.fe.user

class AddCourseFragment : Fragment() {
    private var _binding: FragmentAddCourseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodosViewModel by activityViewModels { TodoViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategorySpinner()
        setupObservers()

        binding.btnCancel.setOnClickListener {
            navigateToDashboardFragment()
        }

        binding.btnAddCourse.setOnClickListener {
            handleValidationAndSave()
        }
    }

    private fun setupCategorySpinner() {
        val categories = arrayOf("mathematics", "science", "art", "technology", "social")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.insertSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                viewModel.reset()
                navigateToDashboardFragment()
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleValidationAndSave() {
        val title = binding.etTitle.text.toString().trim()
        val selectedCategory = binding.spinnerCategory.selectedItem.toString()

        val currentUser = user
        if (currentUser == null || !currentUser.role.equals("teacher", ignoreCase = true)) {
            Toast.makeText(requireContext(), "Akses Ditolak: Hanya untuk Teacher!", Toast.LENGTH_LONG).show()
            return
        }

        if (title.isEmpty()) {
            binding.etTitle.error = "Judul kursus tidak boleh kosong!"
            binding.etTitle.requestFocus()
            return
        }

        val isDuplicate = viewModel.course.value?.any { it.title.equals(title, ignoreCase = true) } == true
        if (isDuplicate) {
            binding.etTitle.error = "Judul kursus sudah digunakan, buat judul lain!"
            binding.etTitle.requestFocus()
            return
        }

        viewModel.insertCourse(title, selectedCategory)
    }

    private fun navigateToDashboardFragment() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




