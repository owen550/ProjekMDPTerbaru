package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.courseTopic
import com.example.fe.databinding.FragmentTopicMaterialsBinding

class TopicMaterialsFragment : Fragment() {
    private var _binding: FragmentTopicMaterialsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodosViewModel by activityViewModels { TodoViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicMaterialsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activeTopic = courseTopic
        if (activeTopic == null) {
            Toast.makeText(requireContext(), "Data topik tidak ditemukan", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return
        }

        setupObservers()

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_topicMaterialsFragment_to_coursActivity)
        }

        binding.btnAddMaterialTopicCourse.setOnClickListener {
            val videoUrl = binding.etVideoUrl.text.toString().trim()
            val attachmentFile = binding.etAttachmentOrDesc.text.toString().trim()

            if (videoUrl.isEmpty()) {
                binding.etVideoUrl.error = "URL Video tidak boleh kosong"
                binding.etVideoUrl.requestFocus()
                return@setOnClickListener
            }

            if (attachmentFile.isEmpty()) {
                binding.etAttachmentOrDesc.error = "File lampiran tidak boleh kosong"
                binding.etAttachmentOrDesc.requestFocus()
                return@setOnClickListener
            }

            viewModel.insertTopicMaterials(
                videoUrl = videoUrl,
                attachmentFile = attachmentFile,
                topicId = activeTopic.id,
                onSuccessRoute = {
                    findNavController().navigate(R.id.action_topicMaterialsFragment_to_coursActivity)
                }
            )
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
