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
        topicAdapter = CourseTopicAdapter(
            { topic -> gotoCourseMaterial(topic) }
        )

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

//        viewModel.onetopicmaterial.observe(viewLifecycleOwner) { onetopicmaterial ->
//            if (onetopicmaterial != null) {
//                materialTopic = onetopicmaterial
//                findNavController().navigate(R.id.activityCourseMateriFragmen)
//                viewModel.reset()
//            }
//        }

        viewModel.onetopicmaterial.observe(viewLifecycleOwner) { material ->
            Log.d("FRAGMENT", "Observer dipanggil: $material")

            if (material != null) {
                materialTopic = material
                findNavController().navigate(R.id.activityCourseMateriFragmen)
                viewModel.reset()
            }
        }

    }

    fun onListener() {
    }

    fun gotoCourseMaterial(topic: CourseTopic){
        courseTopic = topic
        if(topic.content_type == "material" ){ // Materi
            viewModel.getTopicMaterialByIDCourseTopic(topic.id)
        }
        else{ // Quizz
            Toast.makeText(requireContext(),"COMING SOON", Toast.LENGTH_SHORT).show()
        }
    }
}