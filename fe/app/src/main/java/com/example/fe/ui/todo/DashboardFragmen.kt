package com.example.fe.ui.todo

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.courseDetail
import com.example.fe.data.Course
import com.example.fe.databinding.FragmentDashboardBinding
import com.example.fe.user

class DashboardFragmen : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var courseAdapter: CourseAdapter

    private var originalList: List<Course> = emptyList()

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

        setupRecyclerView()
        setupRoleUI()

        viewModel.getAllCourse()

        onObserve()
        onListen()
        setupFilter()
    }

    private fun setupRecyclerView() {
        // bagian siapin rv nya
        courseAdapter = CourseAdapter(object : CourseAdapter.OnCourseClickListener {
            override fun onBookmark(course: Course) {
                // add ke course_enrolment
                Toast.makeText(requireContext(), "Bookmark: ${course.title}", Toast.LENGTH_SHORT).show()
            }

            override fun onOpenDetail(course: Course) {
                // buka activity nya
                courseDetail = course
                findNavController().navigate(R.id.coursActivity)
            }
        })

        // bagian pasanh ke rv nya
        binding.rvCourse.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = courseAdapter
        }
    }

    private fun setupFilter() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilterAndSort()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etCategory.addTextChangedListener(textWatcher)
        binding.etTitle.addTextChangedListener(textWatcher)
    }

    private fun applyFilterAndSort() {
        val searchCategory = binding.etCategory.text.toString().trim()
        val searchTitle = binding.etTitle.text.toString().trim()

        val filteredList = originalList.filter { course ->
            val matchCategory = course.category.contains(searchCategory, ignoreCase = true)
            val matchTitle = course.title.contains(searchTitle, ignoreCase = true)
            matchCategory && matchTitle
        }.sortedWith(
            compareBy<Course> { it.category.lowercase() }
                .thenBy { it.title.lowercase() }
        )

        courseAdapter.submitList(filteredList)
    }

    private fun setupRoleUI() {
        val currentUser = user ?: return

        when (currentUser.role.lowercase()) {
            "teacher" -> {
                val primaryColor = ContextCompat.getColor(requireContext(), R.color.admin_green_primary)
                val secondaryColor = ContextCompat.getColor(requireContext(), R.color.admin_green_secondary)

                binding.btnSort.backgroundTintList = ColorStateList.valueOf(secondaryColor)
                binding.btnSort.setTextColor(primaryColor)

                binding.btnMyCourse.text = "Add Course"
                binding.btnMyCourse.backgroundTintList = ColorStateList.valueOf(secondaryColor)
                binding.btnMyCourse.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            "student" -> {
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

        viewModel.course.observe(viewLifecycleOwner) { courseList ->
            if (courseList != null) {
                val currentUser = user

                if (currentUser != null && currentUser.role.lowercase() == "teacher") {
                    originalList = courseList.filter { course -> course.teacher_id == currentUser.id }
                } else {
                    originalList = courseList
                }

                applyFilterAndSort()
            }
        }
        }


    private fun onListen() {
        binding.btnMyCourse.setOnClickListener {
            if (user?.role?.lowercase() == "teacher") {
                findNavController().navigate(R.id.action_dashboardFragmen_to_addCourseFragment)
            } else {
                Toast.makeText(requireContext(), "My Courses Clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }
}