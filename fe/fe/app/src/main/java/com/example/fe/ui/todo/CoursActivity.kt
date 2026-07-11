package com.example.fe.ui.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.courseDetail
import com.example.fe.courseTopic
import com.example.fe.data.CourseTopic
import com.example.fe.data.TopicMaterial
import com.example.fe.databinding.FragmentCoursActivityBinding
import com.example.fe.materialTopic
import com.example.fe.user

class CoursActivity : Fragment() {
    private var _binding: FragmentCoursActivityBinding? = null
    private val binding get() = _binding!!

    private lateinit var topicAdapter: CourseTopicAdapter

    private val viewModel: TodosViewModel by viewModels {
        TodoViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiSetup()
        setupRoleUI()
        setupRV()
        onObserve()
        onListener()

        courseDetail?.let {
            viewModel.getAllCourseTopicByID(it.id)
            // Fetch teacher name
            viewModel.fetchUserDetail(it.teacher_id)
        }
    }

    private fun uiSetup() {
        courseDetail?.let {
            binding.txtCourseTitle.text = it.title
            binding.txtCourseCategory.text = it.category
            // Description placeholder as it's not in the data model yet
            binding.txtCourseDescription.text = "Welcome to ${it.title}. This course will guide you through all the necessary steps to master this subject."
        }
    }

    private fun setupRoleUI() {
        val currentUser = user ?: return
        if (currentUser.role.lowercase() == "teacher") {
            binding.fabAddCourse.visibility = View.VISIBLE
        } else {
            binding.fabAddCourse.visibility = View.GONE
        }
    }

    private fun setupRV() {
        topicAdapter = CourseTopicAdapter(
            { topic -> gotoCourseMaterial(topic) }
        )

        binding.rvActivity.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topicAdapter
        }
    }

    private fun onObserve() {
        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrEmpty() && msg != "User session invalid") {
                // We don't want to toast every time, maybe just for errors
                if (msg.contains("Gagal") || msg.contains("Error")) {
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.coursedetail.observe(viewLifecycleOwner) { topics ->
            if (topics != null) {
                val currentCourse = courseDetail

                if (currentCourse != null) {
                    val filteredTopics = topics.filter { topic ->
                        topic.course_id == currentCourse.id
                    }
                    topicAdapter.submitList(filteredTopics)
                    binding.txtTotalTopics.text = "${filteredTopics.size} Topics"
                } else {
                    topicAdapter.submitList(emptyList())
                    binding.txtTotalTopics.text = "0 Topics"
                }
            }
        }

        viewModel.oneuser.observe(viewLifecycleOwner) { teacher ->
            if (teacher != null) {
                binding.txtTeacherName.text = "Teacher: ${teacher.name}"
            }
        }

        viewModel.onetopicmaterial.observe(viewLifecycleOwner) { material ->
            Log.d("FRAGMENT", "Observer dipanggil: $material")

            if (material != null) {
                materialTopic = material
                findNavController().navigate(R.id.action_coursActivity_to_activityCourseMateriFragmen)
                viewModel.reset()
            }
        }
    }

    private fun onListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imgProfile.setOnClickListener {
            findNavController().navigate(R.id.profileFragmen)
        }

        binding.fabAddCourse.setOnClickListener {
            // Sesuaikan ID action ini dengan nama transisi ke form tambah materi/topik di nav_graph.xml Anda
            findNavController().navigate(R.id.action_coursActivity_to_addCourseTopicFragment)
        }
    }

    private fun gotoCourseMaterial(topic: CourseTopic) {

        courseTopic = topic

        when (topic.content_type.lowercase()) {

            "material" -> {
                viewModel.getTopicMaterialByIDCourseTopic(topic.id)
            }

            "quiz" -> {
                Toast.makeText(
                    requireContext(),
                    "Quiz coming soon.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
                Toast.makeText(
                    requireContext(),
                    "Unknown topic type.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}