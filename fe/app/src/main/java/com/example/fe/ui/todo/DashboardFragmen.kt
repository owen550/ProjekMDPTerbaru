package com.example.fe.ui.todo

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentDashboardBinding
import com.example.fe.user

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

        setupRoleUI()
        onObserve()
        onListen()

        // Ambil data courses (asumsi ada fungsi ini di viewModel)
        // viewModel.getAllCourses()
    }

    private fun setupRoleUI() {
        val currentUser = user ?: return
        
        when (currentUser.role.lowercase()) {
            "teacher" -> {
                // Tema Hijau untuk Guru
                val primaryColor = ContextCompat.getColor(requireContext(), R.color.admin_green_primary)
                val secondaryColor = ContextCompat.getColor(requireContext(), R.color.admin_green_secondary)
                
                binding.btnSort.backgroundTintList = ColorStateList.valueOf(secondaryColor)
                binding.btnSort.setTextColor(primaryColor)
                
                binding.btnMyCourse.text = "Add Course"
                binding.btnMyCourse.backgroundTintList = ColorStateList.valueOf(secondaryColor)
                binding.btnMyCourse.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            "student" -> {
                // Tema Orange untuk Siswa
                val primaryColor = ContextCompat.getColor(requireContext(), R.color.user_orange_primary)
                val secondaryColor = ContextCompat.getColor(requireContext(), R.color.user_orange_secondary)
                
                binding.btnSort.backgroundTintList = ColorStateList.valueOf(secondaryColor)
                binding.btnSort.setTextColor(primaryColor)
                
                binding.btnMyCourse.text = "My Courses"
                binding.btnMyCourse.backgroundTintList = ColorStateList.valueOf(secondaryColor)
                binding.btnMyCourse.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    private fun onObserve() {
        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onListen() {
        binding.btnMyCourse.setOnClickListener {
            if (user?.role?.lowercase() == "teacher") {
                // Logika tambah course
                Toast.makeText(requireContext(), "Add Course Clicked", Toast.LENGTH_SHORT).show()
            } else {
                // Logika lihat course saya
                Toast.makeText(requireContext(), "My Courses Clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
