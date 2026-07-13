package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.courseDetail
import com.example.fe.databinding.FragmentAddCourseTopicBinding
import com.example.fe.ui.todoform.TodoFormViewModel

class AddCourseTopicFragment : Fragment() {

    private var _binding: FragmentAddCourseTopicBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodosViewModel by activityViewModels { TodoViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCourseTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activeCourse = courseDetail
        if (activeCourse == null) {
            Toast.makeText(requireContext(), "Data kursus tidak ditemukan", Toast.LENGTH_SHORT)
                .show()
            findNavController().popBackStack()
            return
        }

        setupContentTypeSpinner()
        setupObservers()

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAddCourseTopic.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val contentType = binding.spinnerCategory.selectedItem.toString()

            if (title.isEmpty()) {
                binding.etTitle.error = "Judul tidak boleh kosong"
                binding.etTitle.requestFocus()
                return@setOnClickListener
            }

            if (description.isEmpty()) {
                binding.etDescription.error = "Deskripsi tidak boleh kosong"
                binding.etDescription.requestFocus()
                return@setOnClickListener
            }

            viewModel.insertCourseTopic(
                courseId = activeCourse.id,
                title = title,
                description = description,
                contentType = contentType,
                onSuccessRoute = {
                    findNavController().navigate(R.id.action_addCourseTopicFragment_to_topicMaterialsFragment)
                }
            )
        }
    }

    private fun setupContentTypeSpinner() {
        val types = arrayOf("material", "quiz")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            types
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedType = types[position]
                    binding.btnAddCourseTopic.isEnabled = selectedType == "material"
                    binding.btnAddCourseTopic.isEnabled = selectedType == "quiz"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.btnAddCourseTopic.isEnabled = false
                }
            }
    }

    private fun setupObservers() {
        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
