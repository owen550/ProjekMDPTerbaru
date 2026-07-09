package com.example.fe.ui.todo

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import com.example.fe.courseTopic
import com.example.fe.databinding.FragmentActivityCourseMateriBinding
import com.example.fe.materialTopic

class ActivityCourseMateriFragmen : Fragment() {

    private var _binding: FragmentActivityCourseMateriBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivityCourseMateriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiSetup()

        val videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

        try {
            val uri = Uri.parse(videoUrl)
            binding.videoPlayer.setVideoURI(uri)

            val mediaController = MediaController(requireContext())
            mediaController.setAnchorView(binding.videoPlayer)
            binding.videoPlayer.setMediaController(mediaController)

            binding.videoPlayer.requestFocus()

            binding.videoPlayer.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.start()
            }

            binding.videoPlayer.setOnErrorListener { _, what, extra ->
                Toast.makeText(requireContext(), "Error: $what, Extra: $extra", Toast.LENGTH_SHORT).show()
                true
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error load video", Toast.LENGTH_SHORT).show()
        }
    }

    fun uiSetup(){
        binding.txtDetailTitle.setText(courseTopic?.title ?: "No Title")
        binding.txtDetailDesc.setText(materialTopic?.attachment_file ?: "No Attachment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}