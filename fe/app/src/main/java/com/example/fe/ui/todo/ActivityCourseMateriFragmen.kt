package com.example.fe.ui.todo

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.example.fe.courseDetail
import com.example.fe.courseTopic
import com.example.fe.databinding.FragmentActivityCourseMateriBinding
import com.example.fe.materialTopic

class ActivityCourseMateriFragmen : Fragment() {

    private var _binding: FragmentActivityCourseMateriBinding? = null
    // Menggunakan backing property agar aman dari memory leak di Fragmen
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

        // === setup ui ====
        setupUI() // ubh judul dll ntik, sama lek isa cri vidio solusinya

        // === ntik vidio didapet dri sini Tentukan link video online (pastikan link langsung mengarah ke file video, misal format .mp4)
        val videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4"
        val uri = Uri.parse(videoUrl)

        // === Set URI video ke VideoView via binding
        binding.videoPlayer.setVideoURI(uri)

        // === Tambahkan MediaController (Tombol Play, Pause, Seekbar)
        // Menggunakan context dari activity karena MediaController butuh token window WindowManager
        val mediaController = MediaController(requireActivity())
        mediaController.setAnchorView(binding.videoPlayer)
        binding.videoPlayer.setMediaController(mediaController)

        // === Jalankan video secara otomatis setelah selesai proses buffering awal
        binding.videoPlayer.setOnPreparedListener {
            binding.videoPlayer.start()
        }
    }

    fun setupUI(){
        binding.txtDetailTitle.setText(courseTopic!!.title)
        binding.txtDetailDesc.setText(materialTopic!!.attachment_file)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
