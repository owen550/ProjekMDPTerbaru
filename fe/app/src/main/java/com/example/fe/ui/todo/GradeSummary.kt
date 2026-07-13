package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.TodoViewModelFactory // Import Factory
import com.example.fe.databinding.FragmentGradeSummaryBinding

class GradeSummary : Fragment() {

    // Inisialisasi ViewBinding
    private var _binding: FragmentGradeSummaryBinding? = null
    private val binding get() = _binding!!

    // Menghubungkan ViewModel menggunakan TodoViewModelFactory yang kamu punya
    private val viewModel: TodosViewModel by viewModels { TodoViewModelFactory }

    // Deklarasi Adapter
    private lateinit var gradeAdapter: GradeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGradeSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Setup RecyclerView & Adapter
        setupRecyclerView()

        // 2. Setup Observer untuk mendengarkan perubahan data LiveData dari ViewModel
        setupObservers()


    }

    private fun setupRecyclerView() {
        gradeAdapter = GradeAdapter { selectedGrade ->
            // Aksi saat item nilai diklik
            Toast.makeText(
                requireContext(),
                "Melihat detail: ${selectedGrade.course.title}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Pastikan di layout fragment_grade_summary.xml kamu memiliki sebuah RecyclerView bernama rvGrade
        binding.rvGrades.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = gradeAdapter
        }
    }

    private fun setupObservers() {
        // Observe data list rangkuman nilai
        viewModel.gradesumary.observe(viewLifecycleOwner) { listGrade ->
            if (listGrade != null) {
                gradeAdapter.submitList(listGrade)
            }
        }

        // Observe pesan error atau informasi toast jika ada kegagalan backend
        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Menghindari memory leak pada Fragment View Binding
    }
}