package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.courseDetail
import com.example.fe.databinding.FragmentCoursActivityBinding

class CoursActivity : Fragment() {
    lateinit var binding: FragmentCoursActivityBinding
    private lateinit var topicAdapter: CourseTopicAdapter

    private val viewModel: TodosViewModel by viewModels {
        TodoViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllCourseTopicByID(courseDetail!!.id)

        setupRV()
        onObserve()
        onListener()
    }

    fun setupRV() {
        topicAdapter = CourseTopicAdapter()
        binding.rvActivity.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topicAdapter
        }
    }

    fun onObserve() {
        viewModel.message.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.coursedetail.observe(viewLifecycleOwner) { topicList ->
            if (topicList != null) {
                topicAdapter.submitList(topicList)
            }
        }
    }

    fun onListener() {
    }
}